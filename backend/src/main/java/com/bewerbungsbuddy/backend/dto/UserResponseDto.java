package com.bewerbungsbuddy.backend.dto;

public record UserResponseDto(
        Long id,
        String email,
        String fullName,
        String subscriptionPlan
) {
}
