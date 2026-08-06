package com.naike.teamhub.services;

import com.naike.teamhub.data.repositories.TeamRepository;
import com.naike.teamhub.data.repositories.UserRepository;
import com.naike.teamhub.domain.dtos.team.CreateTeamDto;
import com.naike.teamhub.domain.dtos.team.TeamDto;
import com.naike.teamhub.domain.dtos.team.UpdateTeamDto;
import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.domain.mapper.TeamMapper;
import com.naike.teamhub.domain.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;

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
    public List<TeamDto> getAllTeams(){
       return  teamRepository.findAll().stream().map(
                teamMapper::toDto
        ).toList();
    }

    public TeamDto getTeamById(UUID id){
        return teamRepository.findById(id)
                .map(teamMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("team not found"));
    }

    @Transactional
    public TeamDto updateTeam(UUID id, UpdateTeamDto updateTeamDto) {
        return teamRepository.findById(id)
                .map(
                        team -> {
                            Optional.ofNullable(updateTeamDto.getName()).ifPresent(team::setName);
                            Optional.ofNullable(updateTeamDto.getDescription()).ifPresent(team::setDescription);
                            Optional.ofNullable(updateTeamDto.getTeamLeadId()).ifPresent(
                                    teamLeadId -> {
                                        UserEntity teamLead = userRepository.findById(updateTeamDto.getTeamLeadId()).
                                                orElseThrow(() -> new UsernameNotFoundException("user not found"));
                                        team.setTeamLead(teamLead);
                                    }
                            );
                            teamRepository.save(team);
                            return teamMapper.toDto(team);
                        }
                )
                .orElseThrow(() -> new UsernameNotFoundException("team not found"));
    }
    public void deleteTeam(UUID id){
        teamRepository.deleteById(id);
    }
    public List<UserDto> getTeamMembers(UUID teamId){
        return userRepository.findAllByTeamsId(teamId)
                .stream().map(userMapper::toUserDto).toList();
    }
    @Transactional
    public void addMemberToTeam(UUID teamId, UUID userId){
        if(teamRepository.existsByMembersId(userId)){
            throw new DataIntegrityViolationException("User with id " + userId + " is already a member of the team");
        }
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new UsernameNotFoundException("team not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        teamEntity.getMembers().add(user);
        teamRepository.save(teamEntity);
    }

    @Transactional
    public void removeMemberFromTeam(UUID teamId, UUID memberId){
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new UsernameNotFoundException("team not found"));
        UserEntity member = userRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        teamEntity.getMembers().remove(member);
        teamRepository.save(teamEntity);
    }
}
