package com.naike.teamhub.services;

import com.naike.teamhub.domain.dtos.user.LoginDto;
import com.naike.teamhub.spring_security.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final JwtService jwtService;


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
