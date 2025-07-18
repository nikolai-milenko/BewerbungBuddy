package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.repository.UserRepository;
import com.bewerbungsbuddy.backend.service.CVDocumentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CVDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CVDocumentService cvDocumentService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("POST /api/cv/upload - sollte PDF hochladen und DTO zurückgeben")
    void upload_shouldReturnDto() throws Exception {
        when(userRepository.findById(6L)).thenReturn(Optional.of(new User()));

        CVDocumentResponseDto dto = new CVDocumentResponseDto(
                42L,
                "lebenslauf.pdf",
                LocalDateTime.parse("2023-10-05T14:30:00"),
                "parsed text here"
        );
        when(cvDocumentService.saveFromFile(any(), any())).thenReturn(dto);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "lebenslauf.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "dummy".getBytes()
        );

        mockMvc.perform(multipart("/api/cv/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.filename").value("lebenslauf.pdf"))
                .andExpect(jsonPath("$.uploadedAt").value("2023-10-05T14:30:00"))
                .andExpect(jsonPath("$.parsedText").value("parsed text here"));

        verify(cvDocumentService).saveFromFile(any(), any());
    }

    @Test
    @DisplayName("POST /api/cv/upload - sollte 400 bei leerer Datei")
    void upload_shouldReturnBadRequest_whenEmpty() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "",
                MediaType.APPLICATION_PDF_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/api/cv/upload").file(emptyFile))
                .andExpect(status().isBadRequest());

        verify(cvDocumentService, never()).saveFromFile(any(), any());
    }

    @Test
    @DisplayName("POST /api/cv/upload - sollte 400 bei falschem Format")
    void upload_shouldReturnBadRequest_whenNotPdf() throws Exception {
        MockMultipartFile txtFile = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "abc".getBytes()
        );

        mockMvc.perform(multipart("/api/cv/upload").file(txtFile))
                .andExpect(status().isBadRequest());

        verify(cvDocumentService, never()).saveFromFile(any(), any());
    }
}
