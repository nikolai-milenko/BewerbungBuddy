package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.service.CVDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cv")
public class CVDocumentController {

    private final CVDocumentService service;

    public CVDocumentController(CVDocumentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<CVDocumentResponseDto> upload(@RequestParam("file") MultipartFile file) {
        CVDocumentResponseDto dto = service.saveFromFile(file);
        return ResponseEntity.ok(dto);
    }
}

