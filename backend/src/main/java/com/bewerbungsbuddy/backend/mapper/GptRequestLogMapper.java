package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.GptRequestLogResponseDto;
import com.bewerbungsbuddy.backend.entity.GptRequestLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GptRequestLogMapper {

    GptRequestLogResponseDto toResponseDto(GptRequestLog entity);
}
