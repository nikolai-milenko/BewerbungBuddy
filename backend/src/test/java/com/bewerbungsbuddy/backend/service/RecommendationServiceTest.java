package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.RecommendationResponseDto;
import com.bewerbungsbuddy.backend.entity.*;
import com.bewerbungsbuddy.backend.mapper.RecommendationMapper;
import com.bewerbungsbuddy.backend.repository.CVAnalysisRepository;
import com.bewerbungsbuddy.backend.repository.RecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @InjectMocks
    private RecommendationService recommendationService;

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private CVAnalysisRepository cvAnalysisRepository;

    @Mock
    private RecommendationMapper recommendationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRecommendationToAnalysis_shouldSaveRecommendation() {
        // Arrange
        Long cvAnalysisId = 1L;
        CVAnalysis analysis = CVAnalysis.builder().id(cvAnalysisId).build();
        Recommendation recommendation = Recommendation.builder()
                .text("Add more keywords")
                .category(RecommendationCategory.ADD_KEYWORDS)
                .build();

        when(cvAnalysisRepository.findById(cvAnalysisId)).thenReturn(Optional.of(analysis));
        when(recommendationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        recommendationService.addRecommendationToAnalysis(cvAnalysisId, recommendation);

        // Assert
        assertEquals(analysis, recommendation.getCvAnalysis());
        verify(recommendationRepository).save(recommendation);
    }

    @Test
    void addRecommendationToAnalysis_shouldThrowIfAnalysisNotFound() {
        when(cvAnalysisRepository.findById(999L)).thenReturn(Optional.empty());

        Recommendation recommendation = Recommendation.builder()
                .text("Test")
                .category(RecommendationCategory.ADD_KEYWORDS)
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                recommendationService.addRecommendationToAnalysis(999L, recommendation));
    }

    
}
