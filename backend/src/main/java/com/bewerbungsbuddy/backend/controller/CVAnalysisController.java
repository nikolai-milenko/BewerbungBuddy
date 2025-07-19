package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CVAnalysisRequestDto;
import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.service.CVAnalysisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cv-analysis")
@RequiredArgsConstructor
public class CVAnalysisController {

    private final CVAnalysisService cvAnalysisService;

    @PostMapping
    public ResponseEntity<CVAnalysisResponseDto> analyze(@Valid @RequestBody CVAnalysisRequestDto requestDto) {
        return ResponseEntity.ok(cvAnalysisService.analyzeAndSave(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CVAnalysisResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cvAnalysisService.getById(id));
    }

    @GetMapping("/cv/{cvDocumentId}")
    public ResponseEntity<List<CVAnalysisResponseDto>> getAllByCvDocumentId(@PathVariable Long cvDocumentId) {
        return ResponseEntity.ok(cvAnalysisService.getAllByCvDocumentId(cvDocumentId));
    }

    @GetMapping("/job/{jobAdvertisementId}")
    public ResponseEntity<List<CVAnalysisResponseDto>> getAllByJobAdvertisementId(@PathVariable Long jobAdvertisementId) {
        return ResponseEntity.ok(cvAnalysisService.getAllByJobAdvertisementId(jobAdvertisementId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cvAnalysisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
