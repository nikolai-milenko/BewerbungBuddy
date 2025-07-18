package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.service.CVAnalysisService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CVAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    CVAnalysisService cvAnalysisService;

    // POST /api/cv-analysis
    @Test
    @DisplayName("POST /api/cv-analysis - Sollte CV-Analyse durchführen und speichern")
    void analyze_shouldAnalyzeAndSaveCvAnalysis() throws Exception {
        CVAnalysisResponseDto responseDto = new CVAnalysisResponseDto(
                8.5,
                List.of("Starke Programmierkenntnisse", "Gute Teamfähigkeit"),
                List.of("Wenig Berufserfahrung", "Englischkenntnisse verbesserungswürdig"),
                LocalDateTime.parse("2023-10-05T14:30:00")
        );
        when(cvAnalysisService.analyzeAndSave(any())).thenReturn(responseDto);

        mockMvc.perform(post("/api/cv-analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cvDocumentId\":1,\"jobAdvertisementId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchScore").value(8.5))
                .andExpect(jsonPath("$.strengths[0]").value("Starke Programmierkenntnisse"))
                .andExpect(jsonPath("$.weaknesses[1]").value("Englischkenntnisse verbesserungswürdig"))
                .andExpect(jsonPath("$.analysedAt").value("2023-10-05T14:30:00"));

        verify(cvAnalysisService).analyzeAndSave(any());
    }

    @Test
    @DisplayName("POST /api/cv-analysis - Sollte 400 zurückgeben, bei ungültigen Eingabedaten")
    void analyze_shouldReturnBadRequest_whenInvalidInput() throws Exception {
        mockMvc.perform(post("/api/cv-analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(cvAnalysisService, never()).analyzeAndSave(any());
    }

    @Test
    @DisplayName("POST /api/cv-analysis - Sollte 404 zurückgeben, wenn CV nicht existiert")
    void analyze_shouldReturnNotFound_whenCvNotFound() throws Exception {
        doThrow(new EntityNotFoundException("CV-Dokument nicht gefunden"))
                .when(cvAnalysisService).analyzeAndSave(any());

        mockMvc.perform(post("/api/cv-analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cvDocumentId\":999,\"jobAdvertisementId\":2}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("CV-Dokument nicht gefunden"));

        verify(cvAnalysisService).analyzeAndSave(any());
    }

    // GET /api/cv-analysis/{id}
    @Test
    @DisplayName("GET /api/cv-analysis/{id} - Sollte CV-Analyse per ID zurückgeben")
    void getById_shouldReturnCvAnalysis() throws Exception {
        CVAnalysisResponseDto responseDto = new CVAnalysisResponseDto(
                7.2,
                List.of("Gute Ausbildung", "Projektmanagement"),
                List.of("Lücken im Lebenslauf"),
                LocalDateTime.parse("2023-10-06T10:15:00")
        );
        when(cvAnalysisService.getById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/cv-analysis/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchScore").value(7.2))
                .andExpect(jsonPath("$.strengths[1]").value("Projektmanagement"))
                .andExpect(jsonPath("$.weaknesses[0]").value("Lücken im Lebenslauf"))
                .andExpect(jsonPath("$.analysedAt").value("2023-10-06T10:15:00"));

        verify(cvAnalysisService).getById(1L);
    }

    @Test
    @DisplayName("GET /api/cv-analysis/{id} - Sollte 404 zurückgeben, wenn ID nicht existiert")
    void getById_shouldReturnNotFound_whenInvalidId() throws Exception {
        when(cvAnalysisService.getById(999L))
                .thenThrow(new EntityNotFoundException("Analyse nicht gefunden"));

        mockMvc.perform(get("/api/cv-analysis/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Analyse nicht gefunden"));

        verify(cvAnalysisService).getById(999L);
    }

    // GET /api/cv-analysis/cv/{cvDocumentId}
    @Test
    @DisplayName("GET /api/cv-analysis/cv/{cvDocumentId} - Sollte alle Analysen für CV zurückgeben")
    void getAllByCvDocumentId_shouldReturnAnalysesForCv() throws Exception {
        CVAnalysisResponseDto dto1 = new CVAnalysisResponseDto(
                8.0,
                List.of("Java-Kenntnisse"),
                List.of("Wenig Cloud-Erfahrung"),
                LocalDateTime.parse("2023-10-04T09:00:00")
        );
        CVAnalysisResponseDto dto2 = new CVAnalysisResponseDto(
                9.2,
                List.of("Agile Methoden"),
                List.of("Keine Führungserfahrung"),
                LocalDateTime.parse("2023-10-05T11:30:00")
        );
        when(cvAnalysisService.getAllByCvDocumentId(1L)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/cv-analysis/cv/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].matchScore").value(8.0))
                .andExpect(jsonPath("$[1].matchScore").value(9.2))
                .andExpect(jsonPath("$[0].strengths[0]").value("Java-Kenntnisse"))
                .andExpect(jsonPath("$[1].weaknesses[0]").value("Keine Führungserfahrung"));

        verify(cvAnalysisService).getAllByCvDocumentId(1L);
    }

    @Test
    @DisplayName("GET /api/cv-analysis/cv/{cvDocumentId} - Sollte leere Liste zurückgeben, wenn keine Analysen existieren")
    void getAllByCvDocumentId_shouldReturnEmptyList_whenNoData() throws Exception {
        when(cvAnalysisService.getAllByCvDocumentId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cv-analysis/cv/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(cvAnalysisService).getAllByCvDocumentId(1L);
    }

    // GET /api/cv-analysis/job/{jobAdvertisementId}
    @Test
    @DisplayName("GET /api/cv-analysis/job/{jobAdvertisementId} - Sollte alle Analysen für Stellenanzeige zurückgeben")
    void getAllByJobAdvertisementId_shouldReturnAnalysesForJob() throws Exception {
        CVAnalysisResponseDto dto = new CVAnalysisResponseDto(
                6.8,
                List.of("Gute Kommunikationsfähigkeiten"),
                List.of("Begrenzte Branchenkenntnisse"),
                LocalDateTime.parse("2023-10-07T16:45:00")
        );
        when(cvAnalysisService.getAllByJobAdvertisementId(2L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/cv-analysis/job/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].matchScore").value(6.8))
                .andExpect(jsonPath("$[0].strengths[0]").value("Gute Kommunikationsfähigkeiten"))
                .andExpect(jsonPath("$[0].analysedAt").value("2023-10-07T16:45:00"));

        verify(cvAnalysisService).getAllByJobAdvertisementId(2L);
    }

    @Test
    @DisplayName("GET /api/cv-analysis/job/{jobAdvertisementId} - Sollte 404 zurückgeben, wenn Stellenanzeige nicht existiert")
    void getAllByJobAdvertisementId_shouldReturnNotFound_whenJobNotFound() throws Exception {
        when(cvAnalysisService.getAllByJobAdvertisementId(999L))
                .thenThrow(new EntityNotFoundException("Stellenanzeige nicht gefunden"));

        mockMvc.perform(get("/api/cv-analysis/job/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Stellenanzeige nicht gefunden"));

        verify(cvAnalysisService).getAllByJobAdvertisementId(999L);
    }

    // DELETE /api/cv-analysis/{id}
    @Test
    @DisplayName("DELETE /api/cv-analysis/{id} - Sollte Analyse löschen")
    void deleteById_shouldDeleteAnalysis() throws Exception {
        mockMvc.perform(delete("/api/cv-analysis/1"))
                .andExpect(status().isNoContent());

        verify(cvAnalysisService).deleteById(1L);
    }

    @Test
    @DisplayName("DELETE /api/cv-analysis/{id} - Sollte 404 zurückgeben, wenn Analyse nicht existiert")
    void deleteById_shouldReturnNotFound_whenInvalidId() throws Exception {
        doThrow(new EntityNotFoundException("Analyse nicht gefunden"))
                .when(cvAnalysisService).deleteById(999L);

        mockMvc.perform(delete("/api/cv-analysis/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Analyse nicht gefunden"));

        verify(cvAnalysisService).deleteById(999L);
    }

    @Test
    @DisplayName("DELETE /api/cv-analysis/{id} - Sollte 500 zurückgeben, bei Serverfehlern")
    void deleteById_shouldReturnInternalError_whenServiceFails() throws Exception {
        doThrow(new RuntimeException("Datenbankfehler beim Löschen"))
                .when(cvAnalysisService).deleteById(1L);

        mockMvc.perform(delete("/api/cv-analysis/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Datenbankfehler beim Löschen"));

        verify(cvAnalysisService).deleteById(1L);
    }
}