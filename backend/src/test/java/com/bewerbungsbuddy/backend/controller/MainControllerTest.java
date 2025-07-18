package com.bewerbungsbuddy.backend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / -> forward:/index.html")
    void startSeite_shouldForwardToIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/index.html"));
    }

    @Test
    @DisplayName("GET /our-team -> forward:/our-team.html")
    void unserTeam_shouldForward() throws Exception {
        mockMvc.perform(get("/our-team"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/our-team.html"));
    }

    @Test
    @DisplayName("GET /pricing -> forward:/pricing.html")
    void pricing_shouldForward() throws Exception {
        mockMvc.perform(get("/pricing"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/pricing.html"));
    }

    @Test
    @DisplayName("GET /cv-analysis -> forward:/cv-analysis.html")
    void cvAnalysis_shouldForward() throws Exception {
        mockMvc.perform(get("/cv-analysis"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/cv-analysis.html"));
    }

    @Test
    @DisplayName("GET /letter-generation -> forward:/letter-generation.html")
    void letterGeneration_shouldForward() throws Exception {
        mockMvc.perform(get("/letter-generation"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/letter-generation.html"));
    }
}
