package com.example.restapispringsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtTokenDto {
    private String token;
    private String message;
}
