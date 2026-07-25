package com.naike.teamhub.services;

import com.naike.teamhub.data.repositories.UserRepository;
import com.naike.teamhub.domain.dtos.user.UserDto;
import com.naike.teamhub.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(
                userEntity -> userEntity
        ).orElseThrow(() -> new  UsernameNotFoundException(""));
    }
}
