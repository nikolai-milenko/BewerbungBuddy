package com.bewerbungsbuddy.backend.dto;

public record JobAdvertisementResponseDto(
        Long id,
        String rawText,
        String jobTitle,
        String companyName
) {
}
