package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.JobAdvertisementRequestDto;
import com.bewerbungsbuddy.backend.dto.JobAdvertisementResponseDto;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobAdvertisementMapper {
    // RequestDTO -> Entity
    JobAdvertisement toEntity(JobAdvertisementRequestDto dto);

    // Entity -> ResponseDto
    JobAdvertisementResponseDto toDto(JobAdvertisement entity);
}
