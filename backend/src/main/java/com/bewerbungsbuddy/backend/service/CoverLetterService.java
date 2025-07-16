package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.dto.CoverLetterRequestDto;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.CoverLetter;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import com.bewerbungsbuddy.backend.repository.CoverLetterRepository;
import com.bewerbungsbuddy.backend.mapper.CoverLetterMapper;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import com.bewerbungsbuddy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CoverLetterResponseDto generate(CoverLetterRequestDto requestDto) {
        CVDocument cvDocument = cvDocumentRepository.findById(requestDto.cvDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("CVDocument not found with id: " + requestDto.cvDocumentId()));

        JobAdvertisement jobAdvertisement = jobAdvertisementRepository.findById(requestDto.jobAdvertisementId())
                .orElseThrow(() -> new IllegalArgumentException("JobAdvertisement not found with id: " + requestDto.jobAdvertisementId()));

        String generatedText = chatGptService.generateCoverLetter(cvDocument.getParsedText(), jobAdvertisement.getRawText());
        CoverLetter coverLetter = CoverLetter.builder()
                .generatedText(generatedText)
                .editedText(generatedText)
                .cvDocument(cvDocument)
                .jobAdvertisement(jobAdvertisement)
                .build();

        return coverLetterMapper.toResponseDto(coverLetterRepository.save(coverLetter));
    }
}
