package com.naike.teamhub.domain.dtos.team;


import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.ProjectEntity;
import com.naike.teamhub.domain.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private UUID id;

    private String name;

    private String description;

    private UserDto teamLead;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;
}
