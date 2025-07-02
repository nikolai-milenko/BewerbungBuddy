package com.bewerbungsbuddy.backend.controller;

import com.bewerbungsbuddy.backend.dto.JobAdvertisementRequestDto;
import com.bewerbungsbuddy.backend.dto.JobAdvertisementResponseDto;
import com.bewerbungsbuddy.backend.service.JobAdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-advertisements")
@RequiredArgsConstructor
public class JobAdvertisementController {

    private final JobAdvertisementService jobAdvertisementService;

    @PostMapping
    public ResponseEntity<JobAdvertisementResponseDto> create(@RequestBody JobAdvertisementRequestDto dto) {
        JobAdvertisementResponseDto saved = jobAdvertisementService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<JobAdvertisementResponseDto>> findAll() {
        return ResponseEntity.ok(jobAdvertisementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAdvertisementResponseDto> findById(@PathVariable Long id) {
        return jobAdvertisementService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobAdvertisementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
