package com.naike.teamhub.domain.mapper;

import com.naike.teamhub.domain.dtos.user.CreateUserDto;
import com.naike.teamhub.domain.dtos.user.UpdateUserDto;
import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "status", ignore = true)
    UserEntity toEntity(CreateUserDto dto);

    UserEntity toEntity(UpdateUserDto dto);

    UserDto toUserDto(UserEntity userEntity);
}
