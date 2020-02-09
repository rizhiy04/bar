package com.example.Bar.controller;

import com.example.Bar.dto.authenticationDTO.SignInRequestDTO;
import com.example.Bar.dto.authenticationDTO.SignUpRequestDTO;
import com.example.Bar.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){
        authenticationService.signUp(signUpRequestDTO);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public String signIn(@RequestBody SignInRequestDTO signInRequestDTO){
        return authenticationService.signIn(signInRequestDTO);
    }
}
