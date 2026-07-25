package com.naike.teamhub.domain.dtos.user;

import com.naike.teamhub.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {
    private UUID id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;
    @NotEmpty
    @NotBlank
    private String password;

    @NotEmpty
    private String jobTitle;
    @NotEmpty
    private String department;

    @NotNull(message = "Role is required")
    private UserRole role;

}
