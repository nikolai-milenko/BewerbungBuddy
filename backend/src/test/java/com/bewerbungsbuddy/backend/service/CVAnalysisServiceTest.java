package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CVAnalysisRequestDto;
import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.entity.*;
import com.bewerbungsbuddy.backend.mapper.CVAnalysisMapper;
import com.bewerbungsbuddy.backend.repository.CVAnalysisRepository;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CVAnalysisServiceTest {

    @InjectMocks
    private CVAnalysisService cvAnalysisService;

    @Mock
    private CVAnalysisRepository cvAnalysisRepository;
    @Mock
    private CVDocumentRepository cvDocumentRepository;
    @Mock
    private JobAdvertisementRepository jobAdvertisementRepository;
    @Mock
    private CVAnalysisMapper cvAnalysisMapper;
    @Mock
    private ChatGptService chatGptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void analyzeAndSave_shouldReturnResponseDto() {
        // Arrange
        CVAnalysisRequestDto requestDto = new CVAnalysisRequestDto(1L, 2L);

        CVDocument mockCV = new CVDocument();
        mockCV.setId(1L);
        mockCV.setParsedText("CV text");

        JobAdvertisement mockJob = new JobAdvertisement();
        mockJob.setId(2L);
        mockJob.setRawText("Job text");

        Map<String, Object> gptResponse = Map.of(
                "matchScore", "85.0",
                "strengths", List.of("Strong communication", "Team player"),
                "weaknesses", List.of("Lacks leadership experience"),
                "recommendations", List.of("Add more keywords", "Improve formatting")
        );

        CVAnalysis analysisToSave = CVAnalysis.builder()
                .cvDocument(mockCV)
                .jobAdvertisement(mockJob)
                .matchScore(85.0)
                .strengths(List.of("Strong communication", "Team player"))
                .weaknesses(List.of("Lacks leadership experience"))
                .build();

        CVAnalysis savedAnalysis = CVAnalysis.builder()
                .id(100L)
                .cvDocument(mockCV)
                .jobAdvertisement(mockJob)
                .matchScore(85.0)
                .strengths(List.of("Strong communication", "Team player"))
                .weaknesses(List.of("Lacks leadership experience"))
                .recommendations(List.of(
                        Recommendation.builder().text("Add more keywords").build(),
                        Recommendation.builder().text("Improve formatting").build()
                ))
                .build();

        CVAnalysisResponseDto responseDto = new CVAnalysisResponseDto(
                85.0,
                List.of("Strong communication", "Team player"),
                List.of("Lacks leadership experience"),
                savedAnalysis.getAnalysedAt()
        );

        when(cvDocumentRepository.findById(1L)).thenReturn(Optional.of(mockCV));
        when(jobAdvertisementRepository.findById(2L)).thenReturn(Optional.of(mockJob));
        when(chatGptService.analyzeCv(anyString(), anyString(), anyString())).thenReturn(gptResponse);
        when(cvAnalysisRepository.save(any())).thenReturn(savedAnalysis);
        when(cvAnalysisMapper.toResponseDto(savedAnalysis)).thenReturn(responseDto);

        // Act
        CVAnalysisResponseDto result = cvAnalysisService.analyzeAndSave(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(85.0, result.matchScore());
        verify(cvAnalysisRepository).save(any());
    }
}
