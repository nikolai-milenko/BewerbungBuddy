package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CVAnalysisRequestDto;
import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.entity.CVAnalysis;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.mapper.CVAnalysisMapper;
import com.bewerbungsbuddy.backend.repository.CVAnalysisRepository;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CVAnalysisService {

    private final CVAnalysisRepository cvAnalysisRepository;
    private final CVDocumentRepository cvDocumentRepository;
    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final CVAnalysisMapper cvAnalysisMapper;
    // private final NeuralAnalysisService neuralAnalysisService;

    @Transactional
    public CVAnalysisResponseDto analyzeAndSave(CVAnalysisRequestDto requestDto) {
        CVDocument cvDocument = cvDocumentRepository.findById(requestDto.cvDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("CVDocument not found with id: " + requestDto.cvDocumentId()));

        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(requestDto.jobAdvertisementId())
                .orElseThrow(() -> new IllegalArgumentException("JobAdvertisement not found with id: " + requestDto.jobAdvertisementId()));
        /*
        NeuralAnalysisResult analysisResult = neuralAnalysisService.analyze(
                cvDocument.getParsedText(),
                jobAdvertisement.getParsedText()
        );

        CVAnalysis analysis = CVAnalysis.builder()
                .matchScore(analysisResult.matchScore())
                .strengths(analysisResult.strengths())
                .weaknesses(analysisResult.weaknesses())
                .cvDocument(cvDocument)
                .jobAdvertisement(jobAdvertisement)
                .build();

        CVAnalysis saved = cvAnalysisRepository.save(analysis);
        */
        return cvAnalysisMapper.toResponseDto(new CVAnalysis());
    }
    @Transactional(readOnly = true)
    public CVAnalysisResponseDto getById(Long id) {
        CVAnalysis analysis = cvAnalysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CVAnalysis not found with id: " + id));
        return cvAnalysisMapper.toResponseDto(analysis);
    }

    @Transactional(readOnly = true)
    public List<CVAnalysisResponseDto> getAllByCvDocumentId(Long cvDocumentId) {
        List<CVAnalysis> analyses = cvAnalysisRepository.findAllByCvDocument_Id(cvDocumentId);
        return analyses.stream()
                .map(cvAnalysisMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CVAnalysisResponseDto> getAllByJobAdvertisementId(Long jobAdvertisementId) {
        List<CVAnalysis> analyses = cvAnalysisRepository.findAllByJobAdvertisement_Id(jobAdvertisementId);
        return analyses.stream()
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
