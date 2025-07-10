package com.bewerbungsbuddy.backend.dto;

import java.util.List;

public record ChatCompletionResponseDto(
        List<Choice> choices
) {
    public record Choice(
            Message message,
            String finish_reason
    ) {}

    public record Message(
            String role,
            String content
    ) {}
}

