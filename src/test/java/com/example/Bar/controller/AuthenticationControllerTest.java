package com.example.Bar.controller;

import com.example.Bar.entity.UserEntity;
import com.example.Bar.security.Roles;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AllArgsConstructor
class AuthenticationControllerTest extends AbstractControllerTest {

    @Test
    public void testSignUpIsCreated() throws Exception{

        given(userRepository.findByEmail("client@gmail.com")).willReturn(Optional.empty());

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

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("client@gmail.com");
        userEntity.setPassword(passwordEncoder.encode("qwerty"));
        userEntity.setRoles(Roles.CLIENT);

        given(userRepository.findByEmail("client@gmail.com")).willReturn(Optional.of(userEntity));

        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

}