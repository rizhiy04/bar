package com.example.Bar.service;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.exception.SuchUserAlreadyExistException;
import com.example.Bar.exception.WrongPasswordException;
import com.example.Bar.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class AuthenticationServiceTest extends AbstractServiceTest{

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void signUpThrowsException(){
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.of(new UserEntity()));
        assertThrows(SuchUserAlreadyExistException.class, () ->
                authenticationService.signUp(createSignUpRequestDTO()));
    }

    @Test
    public void signInThrowsWrongPasswordException(){
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.of(new UserEntity()));
        assertThrows(WrongPasswordException.class, () ->
                authenticationService.signIn(createSignInRequestDTO()));
    }

    @Test
    public void signInUsernameNotFoundException(){
        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () ->
                authenticationService.signIn(createSignInRequestDTO()));
    }

    private SignUpRequestDTO createSignUpRequestDTO(){
        final SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setEmail("qwerty@gmail.com");
        signUpRequestDTO.setName("qwerty");
        signUpRequestDTO.setPassword("qwerty");

        return signUpRequestDTO;
    }

    private SignInRequestDTO createSignInRequestDTO(){
        final SignInRequestDTO signInRequestDTO = new SignInRequestDTO();
        signInRequestDTO.setEmail("qwerty@gmail.com");
        signInRequestDTO.setPassword("qwerty");

        return signInRequestDTO;
    }

}