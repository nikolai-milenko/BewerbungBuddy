package com.bewerbungsbuddy.backend.dto;

import java.time.LocalDateTime;

public record CoverLetterResponseDto(
        String generatedText,
        String editedText
) {
}
