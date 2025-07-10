package com.bewerbungsbuddy.backend.dto;

public record CoverLetterRequestDto(
        Long userId,
        Long cvDocumentId,
        Long jobAdvertisementId,
        String cvText,
        String jobDescription
) {}