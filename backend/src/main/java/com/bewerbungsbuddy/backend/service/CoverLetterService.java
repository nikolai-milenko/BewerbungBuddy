package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.dto.CoverLetterRequestDto;
import com.bewerbungsbuddy.backend.entity.CoverLetter;
import com.bewerbungsbuddy.backend.repository.CoverLetterRepository;
import com.bewerbungsbuddy.backend.mapper.CoverLetterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;
    private final CoverLetterMapper coverLetterMapper;

    public List<CoverLetterResponseDto> getAll() {
        return coverLetterRepository.findAll().stream()
                .map(coverLetterMapper::toResponseDto)
                .toList();
    }

    public CoverLetterResponseDto getById(Long id) {
        CoverLetter coverLetter = coverLetterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CoverLetter not found with id " + id));
        return coverLetterMapper.toResponseDto(coverLetter);
    }

    //TODO
    public CoverLetterResponseDto generate(CoverLetterRequestDto dto) {
        return new CoverLetterResponseDto("","");
    }
}
