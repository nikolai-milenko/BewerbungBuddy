package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.service.CVDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cv")
@RequiredArgsConstructor
public class CVDocumentController {

    private final CVDocumentService service;

    @PostMapping("/upload")
    public ResponseEntity<CVDocumentResponseDto> upload(@RequestParam("file") MultipartFile file) {
        // TODO: change to getting from SecurityContext
        User user = new User();
        user.setId(1L);  // hardcode for now

        CVDocumentResponseDto dto = service.saveFromFile(file, user);
        return ResponseEntity.ok(dto);
    }
}

