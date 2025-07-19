package com.bewerbungsbuddy.backend.dto;

public record CoverLetterResponseDto(
        String generatedText,
        String editedText
) {
}
