package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.GptRequestLogResponseDto;
import com.bewerbungsbuddy.backend.entity.GptRequestLog;
import com.bewerbungsbuddy.backend.entity.RequestType;
import com.bewerbungsbuddy.backend.service.GptRequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpt-logs")
@RequiredArgsConstructor
public class GptRequestLogController {

    private final GptRequestLogService gptRequestLogService;

    /**
     * test POST
     */
    @PostMapping
    public ResponseEntity<Void> addLog(@RequestBody String requestPayload) {
        GptRequestLog log = GptRequestLog.builder()
                .type(RequestType.ANALYSIS) // hardcode for now
                .requestPayload(requestPayload)
                .responsePayload("stubbed response")
                .build();

        gptRequestLogService.saveLog(log);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GptRequestLogResponseDto>> getAllLogs() {
        return ResponseEntity.ok(gptRequestLogService.getAllLogs());
    }
}
