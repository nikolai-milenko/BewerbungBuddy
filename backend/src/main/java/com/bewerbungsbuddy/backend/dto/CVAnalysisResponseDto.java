package com.bewerbungsbuddy.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CVAnalysisResponseDto(
        Double matchScore,
        List<String> strengths,
        List<String> weaknesses,
        LocalDateTime analysedAt
) {
}
