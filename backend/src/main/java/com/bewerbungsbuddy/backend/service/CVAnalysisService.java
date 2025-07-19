package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CVAnalysisRequestDto;
import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.entity.*;
import com.bewerbungsbuddy.backend.mapper.CVAnalysisMapper;
import com.bewerbungsbuddy.backend.repository.CVAnalysisRepository;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CVAnalysisService {

    private final CVAnalysisRepository cvAnalysisRepository;
    private final CVDocumentRepository cvDocumentRepository;
    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final CVAnalysisMapper cvAnalysisMapper;
    private final ChatGptService chatGptService;

    @Transactional
    public CVAnalysisResponseDto analyzeAndSave(CVAnalysisRequestDto requestDto) {

        CVDocument cvDocument = cvDocumentRepository.findById(requestDto.cvDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("CVDocument not found with id: " + requestDto.cvDocumentId()));

        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(requestDto.jobAdvertisementId())
                .orElseThrow(() -> new IllegalArgumentException("JobAdvertisement not found with id: " + requestDto.jobAdvertisementId()));

        Map<String, Object> gptResult = chatGptService.analyzeCv(
                cvDocument.getParsedText(),
                jobAdvertisement.getRawText(),
                "gpt-4.1-mini"
        );

        CVAnalysis analysis = CVAnalysis.builder()
                .matchScore(parseMatchScore((String) gptResult.get("matchScore")))
                .strengths((List<String>) gptResult.get("strengths"))
                .weaknesses((List<String>) gptResult.get("weaknesses"))
                .cvDocument(cvDocument)
                .jobAdvertisement(jobAdvertisement)
                .build();
        
        List<String> recommendationTexts = (List<String>) gptResult.get("recommendations");
        
        List<Recommendation> recommendations = recommendationTexts.stream()
                .map(text -> 
                        Recommendation.builder()
                                .text(text)
                                .category(categorizeRecommendation(text))
                                .cvAnalysis(analysis)
                                .build())
                .toList();
        
        analysis.setRecommendations(recommendations);
        
        CVAnalysis saved = cvAnalysisRepository.save(analysis);

        return cvAnalysisMapper.toResponseDto(saved);
    }

    private RecommendationCategory categorizeRecommendation(String text) {
        String lower = text.toLowerCase();

        if (lower.contains("keyword") || lower.contains("schlüsselwort") || lower.contains("hinzufügen")) {
            return RecommendationCategory.ADD_KEYWORDS;
        } else if (lower.contains("neu formulieren") || lower.contains("rephrase") || lower.contains("umformulieren")) {
            return RecommendationCategory.REPHRASE_EXPERIENCE;
        } else if (lower.contains("highlight") || lower.contains("erfolge") || lower.contains("leistungen")) {
            return RecommendationCategory.HIGHLIGHT_ACHIEVEMENTS;
        } else if (lower.contains("format") || lower.contains("struktur") || lower.contains("layout")) {
            return RecommendationCategory.FORMAT_IMPROVEMENT;
        } else if (lower.contains("grammatik") || lower.contains("stil") || lower.contains("rechtschreibung")) {
            return RecommendationCategory.GRAMMAR_STYLE;
        } else {
            return RecommendationCategory.ADD_KEYWORDS; // default
        }
    }


    private double parseMatchScore(String scoreString) {
        try {
            return Double.parseDouble(scoreString.replace("%", "").trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Transactional(readOnly = true)
    public CVAnalysisResponseDto getById(Long id) {
        CVAnalysis analysis = cvAnalysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CVAnalysis not found with id: " + id));
        return cvAnalysisMapper.toResponseDto(analysis);
    }

    @Transactional(readOnly = true)
    public List<CVAnalysisResponseDto> getAllByCvDocumentId(Long cvDocumentId) {
        return cvAnalysisRepository.findAllByCvDocument_Id(cvDocumentId).stream()
                .map(cvAnalysisMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CVAnalysisResponseDto> getAllByJobAdvertisementId(Long jobAdvertisementId) {
        return cvAnalysisRepository.findAllByJobAdvertisement_Id(jobAdvertisementId).stream()
                .map(cvAnalysisMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!cvAnalysisRepository.existsById(id)) {
            throw new IllegalArgumentException("CVAnalysis not found with id: " + id);
        }
        cvAnalysisRepository.deleteById(id);
    }
}
