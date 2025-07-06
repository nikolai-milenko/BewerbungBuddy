package com.bewerbungsbuddy.backend.dto;

import com.bewerbungsbuddy.backend.entity.RecommendationCategory;

public record RecommendationResponseDto(
        String text,
        RecommendationCategory category
) {
}
