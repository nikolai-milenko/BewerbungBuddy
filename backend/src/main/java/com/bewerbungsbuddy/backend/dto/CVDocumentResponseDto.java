package com.bewerbungsbuddy.backend.dto;

import java.time.LocalDateTime;

public record CVDocumentResponseDto(
    String filename,
    LocalDateTime uploadedAt,
    String parsedText
) {
}
