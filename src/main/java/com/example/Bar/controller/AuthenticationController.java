package com.example.Bar.controller;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import com.example.Bar.exception.SuchUserAlreadyExistException;
import com.example.Bar.exception.WrongPasswordException;
import com.example.Bar.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public SignInResponse signUp(@Valid @RequestBody final SignUpRequestDTO signUpRequestDTO) throws SuchUserAlreadyExistException, UsernameNotFoundException, WrongPasswordException {
        return authenticationService.signUp(signUpRequestDTO);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(@Valid @RequestBody final SignInRequestDTO signInRequestDTO) throws UsernameNotFoundException, WrongPasswordException{
        return authenticationService.signIn(signInRequestDTO);
    }
}
