package com.example.Bar.controller;

import com.example.Bar.dto.SignInRequestDTO;
import com.example.Bar.dto.SignUpRequestDTO;
import com.example.Bar.service.AuthenticationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/bar")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){}

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public String signIn(@RequestBody SignInRequestDTO signInRequestDTO){
        return authenticationService.signIn(signInRequestDTO);
    }
}
