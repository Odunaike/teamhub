package com.naike.teamhub.domain.model.auth;

import com.naike.teamhub.domain.dtos.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private String mmessage;
    private UserDto data;
}
