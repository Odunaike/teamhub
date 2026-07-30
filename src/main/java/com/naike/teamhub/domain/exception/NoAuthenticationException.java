package com.naike.teamhub.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoAuthenticationException extends BadCredentialsException{
    public NoAuthenticationException(String message) {
        super(message);
    }
}

