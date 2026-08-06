package com.naike.teamhub.controllers;

import com.naike.teamhub.domain.dtos.team.CreateTeamDto;
import com.naike.teamhub.domain.dtos.team.TeamDto;
import com.naike.teamhub.domain.dtos.team.UpdateTeamDto;
import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.mapper.TeamMapper;
import com.naike.teamhub.domain.model.ApiResponse;
import com.naike.teamhub.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping //admin access
    public ResponseEntity<ApiResponse<List<TeamDto>>> getAllTeams(){
        List<TeamDto> teams =  teamService.getAllTeams();
        ApiResponse<List<TeamDto>> response = new ApiResponse<>(
                "Teams Retrieved Successfully",
                teams
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamDto>> getTeamById(
            @PathVariable UUID id
    ){
        TeamDto team = teamService.getTeamById(id);
        ApiResponse<TeamDto> response = new ApiResponse<>(
                "Team Retrieved Successfully",
                team
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/{id}") //admin access
    public ResponseEntity<ApiResponse<TeamDto>> updateTeam(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTeamDto updateTeamDto
    ){
        TeamDto  team = teamService.updateTeam(id, updateTeamDto);
        ApiResponse<TeamDto> response = new ApiResponse<>(
                "Team Updated Successfully",
                team
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("{id}") //admin access
    public ResponseEntity<ApiResponse<Void>> deleteTeam(
            @PathVariable UUID id
    ){
        teamService.deleteTeam(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Team deleted successfully")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}/members")
    public ResponseEntity<ApiResponse<List<UserDto>>> getTeamMembers(
            @PathVariable UUID id
    ){
        List<UserDto> members = teamService.getTeamMembers(id);
        ApiResponse<List<UserDto>> response = new ApiResponse<>(
                "successful",
                members
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("{id}/members") //admin access
    public ResponseEntity<ApiResponse<Void>> addTeamMember(
            @PathVariable UUID id,
            @RequestBody UUID userId
    ){
        teamService.addMemberToTeam(id ,userId );
        ApiResponse<Void> response = new ApiResponse<>(
                "User added successfully",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}/members/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeTeamMember(
            @PathVariable UUID id,
            @PathVariable UUID userId
    ){
        teamService.removeMemberFromTeam(id ,userId);
        ApiResponse<Void> response = new ApiResponse<>(
                "User removed successfully",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
