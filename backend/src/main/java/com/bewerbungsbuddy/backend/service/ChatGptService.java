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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final GptRequestLogRepository logRepository;

    public Map<String, Object> analyzeCv(String cvText, String jobDescription, String model) {
        String prompt = buildAnalysisPrompt(cvText, jobDescription);
        return callGpt(prompt, model, RequestType.CV_ANALYSIS, true);
    }

    public Map<String, Object> analyzeCv(String cvText, String jobDescription) {
        return analyzeCv(cvText, jobDescription, "gpt-4.1-mini");
    }

    public String generateCoverLetter(String cvText, String jobDescription, String model) {
        String prompt = buildCoverLetterPrompt(cvText, jobDescription);
        return (String) callGpt(prompt, model, RequestType.COVER_LETTER, false).get("generatedText");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateCoverLetter(String cvText, String jobDescription) {
        return generateCoverLetter(cvText, jobDescription, "gpt-4.1-mini");
    }

    private Map<String, Object> callGpt(String prompt, String model, RequestType type, boolean expectJson) {
        String url = "https://api.openai.com/v1/chat/completions";

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

        try {
            ResponseEntity<ChatCompletionResponseDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    ChatCompletionResponseDto.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("GPT API returned error: " + response.getStatusCode());
            }

            String content = extractContent(response);
            String cleanedContent = expectJson ? cleanJson(content) : content;

            logRepository.save(GptRequestLog.builder()
                    .type(type)
                    .requestPayload(safeJson(requestBody))
                    .responsePayload(safeJson(response.getBody()))
                    .build()
            );

            if (expectJson) {
                return objectMapper.readValue(cleanedContent, Map.class);
            } else {
                return Map.of("generatedText", cleanedContent);
            }

        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Anfrage an GPT-API: " + e.getMessage(), e);
        }
    }

    private String buildAnalysisPrompt(String cvText, String jobDescription) {
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

    private String buildCoverLetterPrompt(String cvText, String jobDescription) {
        return "Verfasse ein professionelles Motivationsschreiben basierend auf dem folgenden Lebenslauf (CV) " +
                "und der Stellenanzeige. Verwende eine freundliche, aber professionelle Sprache auf Deutsch.\n\n" +
                "Antworte ausschließlich mit dem vollständigen Motivationsschreiben als Fließtext, ohne JSON, ohne Kommentare.\n\n" +
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
        String cleaned = rawResponse.strip();

        if (cleaned.startsWith("```")) {
            cleaned = cleaned.replaceAll("^```(json)?\\s*", "");
            cleaned = cleaned.replaceAll("\\s*```$", "");
        }

        return cleaned.strip();
    }

    private String safeJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "ERROR_SERIALIZING";
        }
    }
}