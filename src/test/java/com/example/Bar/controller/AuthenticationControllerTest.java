package com.example.Bar.controller;

import com.example.Bar.entity.UserEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends AbstractControllerTest{

    @Test
    public void testSignUpIsCreated() throws Exception{
        //given
        final UserEntity testUserEntity = getUserEntity();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty(), Optional.of(testUserEntity));

        //when
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client1@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\",\n" +
                                "  \"name\" : \"Денис\"\n" +
                                "}"))
                .andExpect(status().isCreated());

        verify(userRepository, times(2)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testSignUpThrowsBarSuchUserAlreadyExistException() throws Exception{
        //given
        final UserEntity testUserEntity = getUserEntity();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(testUserEntity));

        //when
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"client1@gmail.com\",\n" +
                        "  \"password\" : \"qwerty\",\n" +
                        "  \"name\" : \"Денис\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void testSignInIsOk() throws Exception{
        //given
        final UserEntity testUserEntity = getUserEntity();
        given(userRepository.findByEmail("client@gmail.com")).willReturn(Optional.of(testUserEntity));

        //when
        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"email\" : \"client@gmail.com\",\n" +
                                "  \"password\" : \"qwerty\"\n" +
                                "}"))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void testSignInThrowsUsernameNotFoundException() throws Exception{
        //given
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        //when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"client@gmail.com\",\n" +
                        "  \"password\" : \"qwerty12345\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void testSignInThrowsBarWrongPasswordException() throws Exception{
        //given
        final UserEntity testUserEntity = getUserEntity();
        given(userRepository.findByEmail("client@gmail.com")).willReturn(Optional.of(testUserEntity));

        //when
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"client@gmail.com\",\n" +
                        "  \"password\" : \"qwerty12345\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByEmail(anyString());
    }


    private UserEntity getUserEntity(){
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("client@gmail.com");
        userEntity.setPassword("$2a$10$X3DIONmNXM1Rs/CExdtt3efVJbX5j8jYRDkos2EcY.cqB7zwFvBFu");
        userEntity.setRole(Roles.CLIENT);

        return userEntity;
    }

}