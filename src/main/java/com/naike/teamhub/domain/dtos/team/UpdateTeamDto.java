package com.naike.teamhub.domain.dtos.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTeamDto {
    private String name;

    private String description;

    private UUID teamLeadId;
}
