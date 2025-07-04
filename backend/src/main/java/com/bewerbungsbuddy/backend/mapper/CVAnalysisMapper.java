package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.CVAnalysisRequestDto;
import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.entity.CVAnalysis;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CVAnalysisMapper {
    CVAnalysisResponseDto toResponseDto(CVAnalysis entity);
}
