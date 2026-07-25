package com.naike.teamhub.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppErrorResponse {
    private int status;
    private String message;
    private HashMap<String, Object> details;
}
