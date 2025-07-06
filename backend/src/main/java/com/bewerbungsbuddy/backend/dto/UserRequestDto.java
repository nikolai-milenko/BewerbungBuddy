package com.bewerbungsbuddy.backend.dto;

public record UserRequestDto(
        String email,
        String password,
        String fullName,
        String subscriptionPlan
) {
}
