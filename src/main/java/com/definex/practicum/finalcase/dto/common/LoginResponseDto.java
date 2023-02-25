package com.definex.practicum.finalcase.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponseDto {

    private String accessToken;
    private final String tokenType = "Bearer ";
    private UUID userId;

    public AuthResponseDto(String accessToken, UUID userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
