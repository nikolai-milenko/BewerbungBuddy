package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.JobAdvertisementRequestDto;
import com.bewerbungsbuddy.backend.dto.JobAdvertisementResponseDto;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.mapper.JobAdvertisementMapper;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobAdvertisementService {

    private final JobAdvertisementRepository jobAdvertisementRepository;
    private final JobAdvertisementMapper jobAdvertisementMapper;

    public JobAdvertisementService(JobAdvertisementRepository jobAdvertisementRepository,
                                   JobAdvertisementMapper jobAdvertisementMapper) {
        this.jobAdvertisementRepository = jobAdvertisementRepository;
        this.jobAdvertisementMapper = jobAdvertisementMapper;
    }

    public JobAdvertisementResponseDto save(JobAdvertisementRequestDto requestDto, User user) {
        JobAdvertisement entity = jobAdvertisementMapper.toEntity(requestDto);
        entity.setUser(user);
        JobAdvertisement saved = jobAdvertisementRepository.save(entity);
        return jobAdvertisementMapper.toDto(saved);
    }

    public List<JobAdvertisementResponseDto> findAll() {
        return jobAdvertisementRepository.findAll().stream()
                .map(jobAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<JobAdvertisementResponseDto> findById(Long id) {
        return jobAdvertisementRepository.findById(id)
                .map(jobAdvertisementMapper::toDto);
    }

    public void deleteById(Long id) {
        jobAdvertisementRepository.deleteById(id);
    }

    public List<JobAdvertisementResponseDto> findAllByUser(User user) {
        return jobAdvertisementRepository.findAllByUser(user).stream()
                .map(jobAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }
}
