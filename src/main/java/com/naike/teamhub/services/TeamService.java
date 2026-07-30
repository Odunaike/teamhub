package com.naike.teamhub.services;

import com.naike.teamhub.data.repositories.TeamRepository;
import com.naike.teamhub.data.repositories.UserRepository;
import com.naike.teamhub.domain.dtos.team.CreateTeamDto;
import com.naike.teamhub.domain.dtos.team.TeamDto;
import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.domain.mapper.TeamMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;

    @Transactional
    public TeamDto createTeam(CreateTeamDto createTeamDto) {
        if(teamRepository.existsByName(createTeamDto.getName()))
            throw new UsernameNotFoundException(String.format("Team with name %s already exists", createTeamDto.getName()));

        TeamEntity teamEntity = teamMapper.toEntity(createTeamDto);
        List<UserEntity> members =  userRepository.findAllById(createTeamDto.getMemberIds());
        UserEntity teamLead = userRepository.findById(createTeamDto.getTeamLeadId()).
                orElseThrow(() -> new UsernameNotFoundException("user not found"));

        teamEntity.setMembers(members);
        teamEntity.setTeamLead(teamLead);

        TeamEntity team = teamRepository.save(teamEntity);
        return teamMapper.toDto(team);
    }
}
