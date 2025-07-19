package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.mapper.CVDocumentMapper;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CVDocumentServiceTest {

    @InjectMocks
    private CVDocumentService cvDocumentService;

    @Mock
    private CVDocumentRepository repository;

    @Mock
    private CVDocumentMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_shouldReturnAllDocuments() {
        CVDocument doc = CVDocument.builder()
                .id(1L)
                .filename("cv.pdf")
                .parsedText("Parsed text")
                .build();

        CVDocumentResponseDto dto = new CVDocumentResponseDto(1L, "cv.pdf", null, "Parsed text");

        when(repository.findAll()).thenReturn(List.of(doc));
        when(mapper.toResponseDto(doc)).thenReturn(dto);

        List<CVDocumentResponseDto> result = cvDocumentService.getAll();

        assertEquals(1, result.size());
        assertEquals("cv.pdf", result.get(0).filename());
        verify(repository).findAll();
    }
}
