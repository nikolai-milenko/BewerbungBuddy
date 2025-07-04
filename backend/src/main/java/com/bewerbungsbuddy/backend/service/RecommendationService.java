package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.RecommendationResponseDto;
import com.bewerbungsbuddy.backend.entity.CVAnalysis;
import com.bewerbungsbuddy.backend.entity.Recommendation;
import com.bewerbungsbuddy.backend.mapper.RecommendationMapper;
import com.bewerbungsbuddy.backend.repository.CVAnalysisRepository;
import com.bewerbungsbuddy.backend.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final CVAnalysisRepository cvAnalysisRepository;
    private final RecommendationMapper recommendationMapper;

    @Transactional
    public void addRecommendationToAnalysis(Long cvAnalysisId, Recommendation recommendation) {
        CVAnalysis analysis = cvAnalysisRepository.findById(cvAnalysisId)
                .orElseThrow(() -> new IllegalArgumentException("CVAnalysis not found with id: " + cvAnalysisId));

        recommendation.setCvAnalysis(analysis);
        recommendationRepository.save(recommendation);
    }

    @Transactional(readOnly = true)
    public List<RecommendationResponseDto> getRecommendationsByAnalysisId(Long cvAnalysisId) {
        List<Recommendation> recommendations = recommendationRepository.findAllByCvAnalysis_Id(cvAnalysisId);
        return recommendations.stream()
                .map(recommendationMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
