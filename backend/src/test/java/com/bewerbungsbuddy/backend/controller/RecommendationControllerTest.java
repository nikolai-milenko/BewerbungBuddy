package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.RecommendationResponseDto;
import com.bewerbungsbuddy.backend.entity.RecommendationCategory;
import com.bewerbungsbuddy.backend.service.RecommendationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecommendationService recommendationService;

    @Test
    @DisplayName("POST /api/recommendations/{id} – sollte Recommendation hinzufügen")
    void addRecommendation_shouldCallServiceAndReturnOk() throws Exception {
        mockMvc.perform(post("/api/recommendations/5")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("füge Schlüsselwörter hinzu"))
                .andExpect(status().isOk());

        verify(recommendationService).addRecommendationToAnalysis(eq(5L), any());
    }

    @Test
    @DisplayName("GET /api/recommendations/{id} – sollte Liste zurückgeben")
    void getRecommendations_shouldReturnList() throws Exception {
        RecommendationResponseDto dto = new RecommendationResponseDto(
                "Text der Empfehlung",
                RecommendationCategory.ADD_KEYWORDS
        );
        when(recommendationService.getRecommendationsByAnalysisId(7L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/recommendations/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Text der Empfehlung"))
                .andExpect(jsonPath("$[0].category").value("ADD_KEYWORDS"));

        verify(recommendationService).getRecommendationsByAnalysisId(7L);
    }
}
