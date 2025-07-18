package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.exception.GlobalExceptionHandler;
import com.bewerbungsbuddy.backend.service.CoverLetterService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CoverLetterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CoverLetterService coverLetterService;

    @InjectMocks
    private CoverLetterController coverLetterController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(coverLetterController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("GET /api/cl - Sollte alle Anschreiben zurückgeben")
    void getAll_shouldReturnAllCoverLetters() throws Exception {
        CoverLetterResponseDto dto = new CoverLetterResponseDto("Generierter Text", "Bearbeiteter Text");
        when(coverLetterService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/cl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].generatedText").value("Generierter Text"))
                .andExpect(jsonPath("$[0].editedText").value("Bearbeiteter Text"));

        verify(coverLetterService).getAll();
    }

    @Test
    @DisplayName("GET /api/cl - Sollte leere Liste zurückgeben, wenn keine Daten existieren")
    void getAll_shouldReturnEmptyList_whenNoData() throws Exception {
        when(coverLetterService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(coverLetterService).getAll();
    }

    @Test
    @DisplayName("GET /api/cl/{id} - Sollte Anschreiben per ID zurückgeben")
    void getById_shouldReturnCoverLetter() throws Exception {
        CoverLetterResponseDto dto = new CoverLetterResponseDto("Generierter Text", "Bearbeiteter Text");
        when(coverLetterService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/cl/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generatedText").value("Generierter Text"))
                .andExpect(jsonPath("$.editedText").value("Bearbeiteter Text"));

        verify(coverLetterService).getById(1L);
    }

    @Test
    @DisplayName("GET /api/cl/{id} - Sollte 404 zurückgeben, wenn ID nicht existiert")
    void getById_shouldReturnNotFound_whenInvalidId() throws Exception {
        when(coverLetterService.getById(999L))
                .thenThrow(new EntityNotFoundException("CoverLetter not found"));

        mockMvc.perform(get("/api/cl/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("CoverLetter not found"));

        verify(coverLetterService).getById(999L);
    }

    @Test
    @DisplayName("POST /api/cl - Sollte neues Anschreiben generieren")
    void generate_shouldCreateCoverLetter() throws Exception {
        CoverLetterResponseDto dto = new CoverLetterResponseDto("Generierter Text", null);
        when(coverLetterService.generate(any())).thenReturn(dto);

        mockMvc.perform(post("/api/cl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cvDocumentId\":1,\"jobAdvertisementId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generatedText").value("Generierter Text"));

        verify(coverLetterService).generate(any());
    }
}