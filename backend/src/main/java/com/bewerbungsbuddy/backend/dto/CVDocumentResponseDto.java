package com.bewerbungsbuddy.backend.dto;

import java.time.LocalDateTime;

public record CVDocumentResponseDto(
    Long id,
    String filename,
    LocalDateTime uploadedAt,
    String parsedText
) {
}
