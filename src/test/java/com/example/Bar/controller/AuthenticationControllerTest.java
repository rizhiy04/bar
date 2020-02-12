package com.example.Bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthenticationControllerTest extends AbstractControllerTest {

    @Test
    public void testSignUpIsCreated() throws Exception{
        final User user = new User("client@gmail.com", passwordEncoder.encode("qwerty"), List.of(new SimpleGrantedAuthority("CLIENT")));
        when(loadUserDetailService.loadUserByUsername("client@gmail.com")).thenReturn(user);

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\",\n" +
                                "  \"name\" : \"Денис\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSignInIsOk() throws Exception{
        final User user = new User("client@gmail.com", passwordEncoder.encode("qwerty"), List.of(new SimpleGrantedAuthority("CLIENT")));
        when(loadUserDetailService.loadUserByUsername("client@gmail.com")).thenReturn(user);

        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

}