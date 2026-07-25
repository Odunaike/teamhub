package com.naike.teamhub.controllers;

import com.naike.teamhub.domain.dtos.user.CreateUserDto;
import com.naike.teamhub.domain.dtos.user.LoginDto;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.domain.mapper.UserMapper;
import com.naike.teamhub.domain.model.auth.CreateUserResponse;
import com.naike.teamhub.domain.model.auth.LoginResponse;
import com.naike.teamhub.services.JwtService;
import com.naike.teamhub.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserDto createUserDto
    ){
        UserEntity user = userMapper.toEntity(createUserDto);
        UserEntity createdUser = authService.createUser(user);
        CreateUserResponse signupResponse = CreateUserResponse.builder()
                .mmessage("User created successfully")
                .data(userMapper.toUserDto(createdUser))
                .build();
        return new ResponseEntity<>(signupResponse,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginDto loginDto
    ){
        UserDetails userDetails = authService.authenticate(loginDto);
        log.info("Login successful for user {}", userDetails.getUsername());
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expiresIn("3 minutes")
                .build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<Object> refresh(
            @RequestBody Map<String, String> refreshBody
    ){
        String token = refreshBody.get("refresh_token");
        if (authService.authenticateRefreshToken(token)){
            UserDetails userDetails = jwtService.extractUserDetails(token);
            String newToken = jwtService.generateToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);
            LoginResponse loginResponse = LoginResponse.builder()
                    .token(newToken)
                    .refreshToken(newRefreshToken)
                    .expiresIn("3 minutes")
                    .build();
            return new ResponseEntity<>( loginResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
