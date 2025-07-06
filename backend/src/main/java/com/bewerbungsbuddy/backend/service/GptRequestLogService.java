package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.GptRequestLogResponseDto;
import com.bewerbungsbuddy.backend.entity.GptRequestLog;
import com.bewerbungsbuddy.backend.mapper.GptRequestLogMapper;
import com.bewerbungsbuddy.backend.repository.GptRequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GptRequestLogService {

    private final GptRequestLogRepository gptRequestLogRepository;
    private final GptRequestLogMapper gptRequestLogMapper;

    @Transactional
    public void saveLog(GptRequestLog log) {
        gptRequestLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<GptRequestLogResponseDto> getAllLogs() {
        return gptRequestLogRepository.findAll().stream()
                .map(gptRequestLogMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
