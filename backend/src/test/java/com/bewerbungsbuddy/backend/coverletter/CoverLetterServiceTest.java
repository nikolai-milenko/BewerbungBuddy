package com.bewerbungsbuddy.backend.coverletter;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.entity.CoverLetter;
import com.bewerbungsbuddy.backend.mapper.CoverLetterMapper;
import com.bewerbungsbuddy.backend.repository.CoverLetterRepository;
import com.bewerbungsbuddy.backend.service.CoverLetterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoverLetterServiceTest {
    @InjectMocks
    private CoverLetterService service;

    @Mock
    private CoverLetterRepository repository;
    @Mock
    private CoverLetterMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        CoverLetter coverLetter = new CoverLetter();
        CoverLetterResponseDto dto = new CoverLetterResponseDto("gen", "edit");

        when(repository.findAll()).thenReturn(List.of(coverLetter));
        when(mapper.toResponseDto(coverLetter)).thenReturn(dto);

        List<CoverLetterResponseDto> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("gen", result.get(0).generatedText());
    }

    @Test
    void getById_shouldReturnDto_whenFound() {
        CoverLetter coverLetter = new CoverLetter();
        coverLetter.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(coverLetter));
        when(mapper.toResponseDto(coverLetter)).thenReturn(new CoverLetterResponseDto("x", "y"));

        CoverLetterResponseDto result = service.getById(1L);

        assertEquals("x", result.generatedText());
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getById(1L));
        assertTrue(ex.getMessage().contains("CoverLetter not found"));
    }

}
