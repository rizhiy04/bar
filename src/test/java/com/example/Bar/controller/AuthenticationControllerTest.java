package com.example.Bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class AuthenticationControllerTest{

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testSignUpIsCreated() throws Exception{

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client1@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\",\n" +
                                "  \"name\" : \"Денис\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSignInIsOk() throws Exception{

        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

}