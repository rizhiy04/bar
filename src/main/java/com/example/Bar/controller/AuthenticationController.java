package com.example.Bar.controller;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import com.example.Bar.exception.BarSuchUserAlreadyExistException;
import com.example.Bar.exception.BarWrongPasswordException;
import com.example.Bar.service.AuthenticationService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@Api(value="Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "SignUp", notes = "Use this method to signUp new User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 400, message = "User already exist")
    })
    public SignInResponse signUp(@ApiParam(value = "User signUp data", required = true)
            @Valid @RequestBody final SignUpRequestDTO signUpRequestDTO) throws BarSuchUserAlreadyExistException, UsernameNotFoundException, BarWrongPasswordException {
        return authenticationService.signUp(signUpRequestDTO);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "SignIn", notes = "Use this method to signIn, if user doesn't exist")
    public SignInResponse signIn(@ApiParam(value = "User signIn data", required = true)
            @Valid @RequestBody final SignInRequestDTO signInRequestDTO) throws UsernameNotFoundException, BarWrongPasswordException {
        return authenticationService.signIn(signInRequestDTO);
    }
}
