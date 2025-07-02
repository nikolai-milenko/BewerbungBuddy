package com.bewerbungsbuddy.backend.dto;

public record JobAdvertisementResponseDto(
        String rawText,
        String jobTitle,
        String companyName
) {
}
