package com.bewerbungsbuddy.backend.coverletter;

import com.bewerbungsbuddy.backend.controller.CoverLetterController;
import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.service.CoverLetterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {
        CoverLetterController.class,
        CoverLetterControllerTest.MockConfig.class
})
class CoverLetterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoverLetterService coverLetterService;

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(coverLetterService.getAll()).thenReturn(List.of(
                new CoverLetterResponseDto("Generated", "Edited")
        ));

        mockMvc.perform(get("/api/cl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].generatedText").value("Generated"));
    }

    @Test
    void getById_shouldReturnCoverLetter() throws Exception {
        when(coverLetterService.getById(1L))
                .thenReturn(new CoverLetterResponseDto("gen", "edit"));

        mockMvc.perform(get("/api/cl/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.generatedText").value("gen"));
    }

    @Configuration
    static class MockConfig {
        @Bean
        public CoverLetterService coverLetterService() {
            return Mockito.mock(CoverLetterService.class);
        }
    }
}