package com.example.Bar.service;

import com.example.Bar.dto.authenticationDTO.SignInRequestDTO;
import com.example.Bar.dto.authenticationDTO.SignUpRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public void signUp(SignUpRequestDTO signUpRequestDTO){}

    public String signIn(SignInRequestDTO signInRequestDTO){
        return "{\n" +
                "  \"name\" : \"Денис\",\n" +
                "  \"email\" : \"client@gmail.com\",\n" +
                "  \"discount\" : 10,\n" +
                "  \"allSpentMoney\" : 1250\n" +
                "}";
    }

}
