package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.ChatCompletionResponseDto;
import com.bewerbungsbuddy.backend.entity.GptRequestLog;
import com.bewerbungsbuddy.backend.entity.RequestType;
import com.bewerbungsbuddy.backend.repository.GptRequestLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GptRequestLogRepository logRepository;

    public Map<String, Object> analyzeCv(String cvText, String jobDescription, String model) {
        String url = "https://api.openai.com/v1/chat/completions";
        String prompt = buildPrompt(cvText, jobDescription);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "temperature", 0.3,
                "messages", List.of(
                        Map.of("role", "system", "content", "Du bist ein erfahrener HR-Analyst."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ChatCompletionResponseDto> response =
                restTemplate.exchange(url, HttpMethod.POST, request, ChatCompletionResponseDto.class);

        String responseContent = extractContent(response);
        String cleanContent = cleanJson(responseContent);

        logRepository.save(GptRequestLog.builder()
                .type(RequestType.CV_ANALYSIS)
                .requestPayload(safeJson(requestBody))
                .responsePayload(safeJson(response.getBody()))
                .build()
        );

        try {
            return objectMapper.readValue(cleanContent, Map.class);
        } catch (Exception e) {
            System.out.println(responseContent);
            throw new RuntimeException("Fehler beim Parsen der GPT-Antwort: " + e.getMessage());
        }
    }

    private String buildPrompt(String cvText, String jobDescription) {
        return "Vergleiche den folgenden Lebenslauf (CV) mit der untenstehenden Stellenanzeige. " +
                "Gib die Antwort ausschließlich im JSON-Format mit diesen Feldern zurück:\n" +
                "- matchScore: Prozentzahl (z.B. \"75 %\"), wie gut der CV zur Stellenanzeige passt.\n" +
                "- strengths: Liste der Stärken.\n" +
                "- weaknesses: Liste der Schwächen oder fehlenden Qualifikationen.\n" +
                "- recommendations: Liste der Empfehlungen.\n\n" +
                "Antworte ausschließlich als JSON, ohne zusätzliche Kommentare oder Erklärungen.\n\n" +
                "Lebenslauf:\n" +
                cvText + "\n\n" +
                "Stellenanzeige:\n" +
                jobDescription;
    }

    private String extractContent(ResponseEntity<ChatCompletionResponseDto> response) {
        var body = response.getBody();
        if (body == null || body.choices() == null || body.choices().isEmpty()) {
            throw new RuntimeException("GPT hat keine Antwort geliefert.");
        }

        var message = body.choices().getFirst().message();
        if (message == null || message.content() == null) {
            throw new RuntimeException("Antwort ohne Inhalt erhalten.");
        }

        return message.content();
    }

    private String cleanJson(String rawResponse) {
        if (rawResponse.startsWith("```")) {
            return rawResponse.replaceAll("(?s)```.*?\\n", "")
                    .replaceAll("\\n```$", "")
                    .trim();
        }
        return rawResponse.trim();
    }


    private String safeJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "ERROR_SERIALIZING";
        }
    }
}