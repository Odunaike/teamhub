package com.naike.teamhub.domain.mapper;

import com.naike.teamhub.domain.dtos.user.CreateUserDto;
import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity toEntity(CreateUserDto dto);
    CreateUserDto toDto(UserEntity userEntity);
    UserDto toUserDto(UserEntity userEntity);
}
