package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.RecommendationResponseDto;
import com.bewerbungsbuddy.backend.entity.Recommendation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationResponseDto toResponseDto(Recommendation entity);
}
