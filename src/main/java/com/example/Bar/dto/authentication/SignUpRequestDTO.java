package com.example.Bar.dto.authentication;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequestDTO {

    @Email(message = "wrong email")
    @NotBlank(message = "email is blank")
    private String email;

    @Size(min = 6, message = "too short password")
    @NotBlank(message = "password is blank")
    private String password;

    @NotBlank(message = "name is blank")
    private String name;
}
