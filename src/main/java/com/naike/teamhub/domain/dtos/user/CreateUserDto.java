package com.naike.teamhub.domain.dtos.user;

import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.enums.UserRole;
import com.naike.teamhub.domain.enums.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {
    @NotEmpty(message = "firstname must be provided")
    private String firstName;
    @NotEmpty(message = "lastname must be provided")
    private String lastName;
    @Email(message = "Enter a valid mmail")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty
    @NotBlank
    private String password;

    @NotEmpty
    private String jobTitle;
    @NotEmpty
    private String department;

    private List<UUID> teamIds;

    @NotNull(message = "Role is required")
    private UserRole role;

    private LocalDateTime updatedAt;

}
