package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.JobAdvertisementRequestDto;
import com.bewerbungsbuddy.backend.dto.JobAdvertisementResponseDto;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.mapper.JobAdvertisementMapper;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobAdvertisementServiceTest {

    @InjectMocks
    private JobAdvertisementService jobAdvertisementService;

    @Mock
    private JobAdvertisementRepository repository;

    @Mock
    private JobAdvertisementMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldReturnDto() {
        JobAdvertisementRequestDto requestDto = new JobAdvertisementRequestDto("raw text", "Developer", "Acme Inc");
        User user = new User();
        JobAdvertisement entity = JobAdvertisement.builder()
                .rawText("raw text")
                .jobTitle("Developer")
                .companyName("Acme Inc")
                .user(user)
                .build();
        JobAdvertisement savedEntity = JobAdvertisement.builder()
                .id(1L)
                .rawText("raw text")
                .jobTitle("Developer")
                .companyName("Acme Inc")
                .user(user)
                .build();
        JobAdvertisementResponseDto responseDto = new JobAdvertisementResponseDto(1L, "raw text", "Developer", "Acme Inc");

        when(mapper.toEntity(requestDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(responseDto);

        JobAdvertisementResponseDto result = jobAdvertisementService.save(requestDto, user);

        assertNotNull(result);
        assertEquals("Developer", result.jobTitle());
        verify(repository).save(entity);
    }

    @Test
    void findAll_shouldReturnListOfDtos() {
        JobAdvertisement entity = JobAdvertisement.builder()
                .id(1L)
                .rawText("raw")
                .jobTitle("Dev")
                .companyName("X")
                .build();
        JobAdvertisementResponseDto dto = new JobAdvertisementResponseDto(1L, "raw", "Dev", "X");

        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        List<JobAdvertisementResponseDto> result = jobAdvertisementService.findAll();

        assertEquals(1, result.size());
        assertEquals("Dev", result.get(0).jobTitle());
        verify(repository).findAll();
    }
}
