package com.example.Bar.service;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public void signUp(final SignUpRequestDTO signUpRequestDTO){}

    public String signIn(final SignInRequestDTO signInRequestDTO){
        return "{\n" +
                "  \"name\" : \"Денис\",\n" +
                "  \"email\" : \"client@gmail.com\",\n" +
                "  \"discount\" : 10,\n" +
                "  \"allSpentMoney\" : 1250\n" +
                "}";
    }

}
