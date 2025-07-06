package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.CVAnalysisRequestDto;
import com.bewerbungsbuddy.backend.dto.CVAnalysisResponseDto;
import com.bewerbungsbuddy.backend.entity.CVAnalysis;

import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CVAnalysisMapper {
    CVAnalysisResponseDto toResponseDto(CVAnalysis entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "matchScore", ignore = true)
    @Mapping(target = "strengths", ignore = true)
    @Mapping(target = "weaknesses", ignore = true)
    @Mapping(target = "analysedAt", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    @Mapping(target = "cvDocument", source = "cvDocument")
    @Mapping(target = "jobAdvertisement", source = "jobAdvertisement")
    CVAnalysis toEntity(
            CVAnalysisRequestDto dto,
            CVDocument cvDocument,
            JobAdvertisement jobAdvertisement
    );
}
