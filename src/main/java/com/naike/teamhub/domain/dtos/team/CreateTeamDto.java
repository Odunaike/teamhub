package com.naike.teamhub.domain.dtos.team;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTeamDto {

    @NotEmpty(message = "name is required")
    private String name;

    private String description;

    private UUID teamLeadId;

    private List<UUID> memberIds;

    private LocalDateTime updatedAt;
}
