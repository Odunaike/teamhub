package com.naike.teamhub.domain.mapper;

import com.naike.teamhub.domain.dtos.team.CreateTeamDto;
import com.naike.teamhub.domain.dtos.team.TeamDto;
import com.naike.teamhub.domain.entities.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING ,
        uses = {UserMapper.class}
)
public interface TeamMapper {
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "teamLead", ignore = true)
    TeamEntity toEntity(CreateTeamDto createTeamDto);

    @Mapping(target = "teamLead", source = "teamLead")
    TeamDto toDto(TeamEntity teamEntity);

    CreateTeamDto toCreateTeamDto(TeamEntity teamEntity);
}
