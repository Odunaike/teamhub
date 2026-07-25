package com.naike.teamhub.controllers;

import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.domain.mapper.UserMapper;
import com.naike.teamhub.services.UserService;
import com.naike.teamhub.spring_security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        UserEntity user = userService.getUserByEmail(userDetails.getUsername());
        UserDto userDto = userMapper.toUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }
}
