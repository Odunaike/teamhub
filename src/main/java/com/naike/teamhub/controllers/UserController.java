package com.naike.teamhub.controllers;

import com.naike.teamhub.domain.dtos.user.CreateUserDto;
import com.naike.teamhub.domain.dtos.user.UpdateUserDto;
import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.domain.mapper.UserMapper;
import com.naike.teamhub.domain.model.ApiResponse;
import com.naike.teamhub.services.UserService;
import com.naike.teamhub.utility.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityUtil securityUtil;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(
            @Valid @RequestBody CreateUserDto createUserDto
    ){
        UserEntity createdUser = userService.createUser(createUserDto);
        ApiResponse<UserDto> signupResponse = ApiResponse.<UserDto>builder()
                .message("User created successfully")
                .data(userMapper.toUserDto(createdUser))
                .build();
        return new ResponseEntity<>(signupResponse,HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        UUID userId = securityUtil.getCurrentUserId();
        UserEntity user = userService.getUserById(userId);
        UserDto userDto = userMapper.toUserDto(user);
        ApiResponse<UserDto> response = new ApiResponse<>(
                "successful",
                userDto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable UUID id) {
        UserEntity user = userService.getUserById(id);
        UserDto userDto = userMapper.toUserDto(user);
        ApiResponse<UserDto> response = new ApiResponse<>(
                "successful",
                userDto
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping  //For admin alone
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserEntity>  retrievedUsers = userService.getAllUsers();
        List<UserDto> users = retrievedUsers.stream().map(userMapper::toUserDto).toList();
        ApiResponse<List<UserDto>> response = new ApiResponse<>(
                "successful",
                users
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserDto userDto
    ) {
        UserEntity updatedUser = userService.updateUser(id, userDto);
        ApiResponse<UserDto> response = new ApiResponse<>(
                "successful",
                userMapper.toUserDto(updatedUser)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/{id}/deactivate") //admin alone
    public ResponseEntity<ApiResponse<Void>> deactivateUser(
            @PathVariable UUID id
    ){
        userService.deactivateUser(id);
        ApiResponse<Void> response = new ApiResponse<>(
                "User has been deactivated",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
