package com.naike.teamhub.controllers;

import com.naike.teamhub.domain.model.AppErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        HashMap<String,Object> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( error ->{
            map.put(error.getField(), error.getDefaultMessage());
        });
        AppErrorResponse apiErrorResponse = AppErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid request body")
                .details(map).build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        AppErrorResponse appErrorResponse = AppErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(appErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<AppErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        AppErrorResponse appErrorResponse = AppErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("expired token")
                .build();
        return new ResponseEntity<>(appErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AppErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        AppErrorResponse appErrorResponse = AppErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid email or password")
                .build();
        return new ResponseEntity<>(appErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AppErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        AppErrorResponse appErrorResponse = AppErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(appErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<AppErrorResponse> handleException(Exception ex){
        HashMap<String,Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        AppErrorResponse appErrorResponse = AppErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal server error")
                .details(map).build();
        return new ResponseEntity<>(appErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
