package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.mapper.CVDocumentMapper;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;

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

    public CVDocumentResponseDto saveFromFile(MultipartFile file, User user) {
        String filename = file.getOriginalFilename();
        String parsedText = parseFile(file);

        CVDocument entity = CVDocument.builder()
                .filename(filename)
                .parsedText(parsedText)
                .user(user)
                .build();

        return mapper.toResponseDto(repository.save(entity));
    }


    private String parseFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "";
        }

        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document).trim();

        } catch (IOException e) {
            // empty for now
            return "";
        }
    }
}

