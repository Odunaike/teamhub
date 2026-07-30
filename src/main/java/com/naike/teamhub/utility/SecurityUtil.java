package com.naike.teamhub.utility;

import com.naike.teamhub.domain.exception.NoAuthenticationException;
import com.naike.teamhub.spring_security.AppUserDetails;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtil {

    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null && !authentication.isAuthenticated()){
            throw new NoAuthenticationException("No authentication");
        }
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }

}
