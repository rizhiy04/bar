package com.example.Bar.controller;

import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.repository.*;
import com.example.Bar.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class AbstractControllerTest {

    @Autowired
    protected  MockMvc mockMvc;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    protected String signIn(final Roles role) throws Exception{
        List<UserEntity> te = userRepository.findAll();
        final UserEntity userEntity = userRepository.findAllByRole(role).stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("No such username"));

        final String response = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"" + userEntity.getEmail() + "\",\n" +
                        "  \"password\" : \"qwerty\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return "Bearer " + objectMapper.readValue(response, SignInResponse.class).getToken();
    }
}
