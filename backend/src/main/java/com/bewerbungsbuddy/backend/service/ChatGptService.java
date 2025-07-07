package com.bewerbungsbuddy.backend.service;

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

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GptRequestLogRepository logRepository;

    public Map<String, Object> analyzeCv(String cvText, String jobDescription) {
        String url = "https://api.openai.com/v1/chat/completions";
        String prompt = buildPrompt(cvText, jobDescription);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1mini",
                "temperature", 0.3,
                "messages", List.of(
                        Map.of("role", "system", "content", "Du bist ein erfahrener HR-Analyst."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
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

    @SuppressWarnings("unchecked")
    private String extractContent(ResponseEntity<Map> response) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("GPT hat keine Antwort geliefert.");
        }
        Map<String, Object> message = (Map<String, Object>) choices.getFirst().get("message");
        return (String) message.get("content");
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