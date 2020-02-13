package com.example.Bar.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDTO {

    @Email(message = "wrong email")
    @NotBlank(message = "email is blank")
    private String email;

    @Size(min = 6, message = "too short password")
    @NotBlank(message = "password is blank")
    private String password;
}
