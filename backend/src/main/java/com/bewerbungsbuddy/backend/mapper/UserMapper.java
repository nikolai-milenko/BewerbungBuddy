package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.UserRequestDto;
import com.bewerbungsbuddy.backend.dto.UserResponseDto;
import com.bewerbungsbuddy.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptionPlan", expression = "java(com.bewerbungsbuddy.backend.entity.SubscriptionType.valueOf(dto.subscriptionPlan()))")
    User toEntity(UserRequestDto dto);

    @Mapping(target = "subscriptionPlan", expression = "java(user.getSubscriptionPlan().name())")
    UserResponseDto toResponseDto(User user);
}
