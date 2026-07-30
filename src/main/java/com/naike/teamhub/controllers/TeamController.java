package com.naike.teamhub.controllers;

import com.naike.teamhub.domain.dtos.team.CreateTeamDto;
import com.naike.teamhub.domain.dtos.team.TeamDto;
import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.mapper.TeamMapper;
import com.naike.teamhub.domain.model.ApiResponse;
import com.naike.teamhub.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping //admmin access
    public ResponseEntity<ApiResponse<TeamDto>> createTeam(
           @Valid @RequestBody CreateTeamDto createTeamDto
            ){
        TeamDto teamDto = teamService.createTeam(createTeamDto);

       ApiResponse<TeamDto> response =  ApiResponse.<TeamDto>builder()
               .message("Team created successfully")
               .data(teamDto)
               .build();
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
