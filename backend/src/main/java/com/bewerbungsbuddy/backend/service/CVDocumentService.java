package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.mapper.CVDocumentMapper;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CVDocumentService {

    private final CVDocumentRepository repository;
    private final CVDocumentMapper mapper;

    public List<CVDocumentResponseDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public CVDocumentResponseDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("CVDocument not found with id " + id));
    }

    public CVDocumentResponseDto saveFromFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String parsedText = parseFile(file);

        CVDocument entity = CVDocument.builder()
                .filename(filename)
                .parsedText(parsedText)
                .build();

        return mapper.toResponseDto(repository.save(entity));
    }

    private String parseFile(MultipartFile file) {
        // TODO: add PDF parsing
        return "stub";
    }
}

