package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.CVDocumentResponseDto;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CVDocumentMapper {
    // TODO: RequestDto -> Entity

    // Entity -> ResponseDto
    CVDocumentResponseDto toResponseDto(CVDocument cvDocument);
}
