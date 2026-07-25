package com.naike.teamhub.services;

import com.naike.teamhub.data.repositories.UserRepository;
import com.naike.teamhub.domain.dtos.user.LoginDto;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.spring_security.AppUserDetails;
import com.naike.teamhub.spring_security.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserEntity createUser(UserEntity userEntity) {
        if(userRepository.existsByEmail(userEntity.getEmail()))
            throw new DataIntegrityViolationException("Email already in use");
        userEntity.setPassword(
                passwordEncoder.encode(userEntity.getPassword())
        );
         return userRepository.save(userEntity);
    }

    public UserDetails authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
        );
        return appUserDetailsService.loadUserByUsername(loginDto.getEmail());
    }

    public boolean authenticateRefreshToken(String token){
        UserDetails userDetails = jwtService.extractUserDetails(token);
        return jwtService.validateToken(token , userDetails);
    }
}
