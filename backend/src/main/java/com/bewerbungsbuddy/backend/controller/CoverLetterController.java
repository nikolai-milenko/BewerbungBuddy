package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CoverLetterRequestDto;
import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.service.CoverLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/cl")
@RequiredArgsConstructor
public class CoverLetterController {
    private final CoverLetterService coverLetterService;


    @GetMapping
    public ResponseEntity<List<CoverLetterResponseDto>> getAll() {
        return ResponseEntity.ok(coverLetterService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoverLetterResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coverLetterService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CoverLetterResponseDto> generate(@RequestBody CoverLetterRequestDto dto) {
        return ResponseEntity.ok(coverLetterService.generate(dto));
    }
}
