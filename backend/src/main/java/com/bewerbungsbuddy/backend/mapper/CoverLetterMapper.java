package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.dto.CoverLetterRequestDto;
import com.bewerbungsbuddy.backend.entity.CoverLetter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoverLetterMapper {

    CoverLetter toEntity(CoverLetterRequestDto dto);

    // Из сущности в DTO
    CoverLetterResponseDto toResponseDto(CoverLetter coverLetter);
}
