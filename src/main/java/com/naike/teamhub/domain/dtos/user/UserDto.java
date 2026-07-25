package com.naike.teamhub.domain.dtos.user;

import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.enums.UserRole;
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
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String jobTitle;
    private String department;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
