package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.JobAdvertisementRequestDto;
import com.bewerbungsbuddy.backend.dto.JobAdvertisementResponseDto;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.service.JobAdvertisementService;
import com.bewerbungsbuddy.backend.service.UserService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobAdvertisementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobAdvertisementService jobAdvertisementService;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/job-advertisements - sollte neues Inserat erstellen")
    void create_shouldSaveAndReturnDto() throws Exception {
        when(userService.getById(6L)).thenReturn(new User());
        JobAdvertisementResponseDto savedDto = new JobAdvertisementResponseDto(
                100L,
                "Vollständiger Rohtext",
                "Java Developer",
                "ACME Corp"
        );
        when(jobAdvertisementService.save(any(), any())).thenReturn(savedDto);

        String requestBody = """
                {
                  "title": "Java Developer",
                  "description": "Awesome Job"
                }
                """;

        mockMvc.perform(post("/api/job-advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.rawText").value("Vollständiger Rohtext"))
                .andExpect(jsonPath("$.jobTitle").value("Java Developer"))
                .andExpect(jsonPath("$.companyName").value("ACME Corp"));

        verify(jobAdvertisementService).save(any(JobAdvertisementRequestDto.class), any(User.class));
    }

    @Test
    @DisplayName("GET /api/job-advertisements - sollte alle Inserate zurückgeben")
    void findAll_shouldReturnList() throws Exception {
        JobAdvertisementResponseDto dto = new JobAdvertisementResponseDto(
                200L,
                "Rohtext 1",
                "Backend Engineer",
                "Globex"
        );
        when(jobAdvertisementService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/job-advertisements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(200))
                .andExpect(jsonPath("$[0].rawText").value("Rohtext 1"))
                .andExpect(jsonPath("$[0].jobTitle").value("Backend Engineer"))
                .andExpect(jsonPath("$[0].companyName").value("Globex"));

        verify(jobAdvertisementService).findAll();
    }

    @Test
    @DisplayName("GET /api/job-advertisements - sollte leere Liste zurückgeben")
    void findAll_shouldReturnEmptyList() throws Exception {
        when(jobAdvertisementService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/job-advertisements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(jobAdvertisementService).findAll();
    }
}
