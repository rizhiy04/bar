package com.example.Bar.service;

import com.example.Bar.dto.SignInRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public String signIn(SignInRequestDTO signInRequestDTO){
        return "{\n" +
                "  \"name\" : \"Денис\",\n" +
                "  \"email\" : \"client@gmail.com\",\n" +
                "  \"discount\" : 10,\n" +
                "  \"allSpentMoney\" : 1250\n" +
                "}";
    }

}
