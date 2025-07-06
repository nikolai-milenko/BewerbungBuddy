package com.bewerbungsbuddy.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record JobAdvertisementRequestDto(
        @NotBlank(message = "Job description cannot be blank.")
        String rawText
) {
}
