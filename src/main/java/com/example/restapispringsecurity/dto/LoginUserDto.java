package com.example.restapispringsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginUserDto {
    private String email;
    private String password;
}
