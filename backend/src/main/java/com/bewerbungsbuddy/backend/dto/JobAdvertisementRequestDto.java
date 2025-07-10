package com.bewerbungsbuddy.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobAdvertisementRequestDto(
        @NotBlank(message = "Job description cannot be blank.")
        String rawText,

        @Size(max = 255, message = "Job title must be at most 255 characters.")
        String jobTitle,

        @Size(max = 100, message = "Company name must be at most 100 characters.")
        String companyName
) {
}
