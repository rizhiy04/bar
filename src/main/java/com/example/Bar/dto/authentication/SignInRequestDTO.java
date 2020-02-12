package com.example.Bar.dto.authentication;

import lombok.Data;

@Data
public class SignInRequestDTO {

    private final String email;
    private final String password;
}
