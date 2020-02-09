package com.example.Bar.dto.authentication;

import lombok.Data;

@Data
public class SignUpRequestDTO {

    private String email;
    private String password;
    private String name;
}
