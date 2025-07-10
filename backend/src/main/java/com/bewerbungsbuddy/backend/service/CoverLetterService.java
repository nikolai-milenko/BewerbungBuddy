package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.dto.CoverLetterRequestDto;
import com.bewerbungsbuddy.backend.entity.CoverLetter;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import com.bewerbungsbuddy.backend.repository.CoverLetterRepository;
import com.bewerbungsbuddy.backend.mapper.CoverLetterMapper;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import com.bewerbungsbuddy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;
    private final CoverLetterMapper coverLetterMapper;
    private final ChatGptService chatGptService;

    private final UserRepository userRepository;
    private final CVDocumentRepository cvDocumentRepository;
    private final JobAdvertisementRepository jobAdvertisementRepository;

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

    public CoverLetterResponseDto generate(CoverLetterRequestDto dto) {
        String generatedText = chatGptService.generateCoverLetter(dto.cvText(), dto.jobDescription());

        CoverLetter coverLetter = coverLetterMapper.toEntity(
                dto,
                userRepository,
                cvDocumentRepository,
                jobAdvertisementRepository
        );
        coverLetter.setGeneratedText(generatedText);
        coverLetter.setEditedText(generatedText);

        return coverLetterMapper.toResponseDto(coverLetterRepository.save(coverLetter));
    }
}
