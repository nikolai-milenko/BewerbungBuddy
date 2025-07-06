package com.bewerbungsbuddy.backend.dto;

import com.bewerbungsbuddy.backend.entity.RequestType;

import java.time.Instant;

public record GptRequestLogResponseDto(
        Long id,
        RequestType type,
        String requestPayload,
        String responsePayload,
        Instant timestamp
) {
}
