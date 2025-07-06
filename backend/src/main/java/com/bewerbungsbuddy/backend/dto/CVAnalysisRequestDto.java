package com.bewerbungsbuddy.backend.dto;

import jakarta.validation.constraints.NotNull;

public record CVAnalysisRequestDto(
        @NotNull
        Long cvDocumentId,
        @NotNull
        Long jobAdvertisementId
) {}
