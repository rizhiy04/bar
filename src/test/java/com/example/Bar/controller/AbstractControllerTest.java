package com.example.Bar.controller;

import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.repository.*;
import com.example.Bar.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractControllerTest {

    @Autowired
    protected  MockMvc mockMvc;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    protected InventoryRepository inventoryRepository;
    @MockBean
    protected EventRepository eventRepository;
    @MockBean
    protected MenuItemRepository menuItemRepository;
    @MockBean
    protected ReservationRepository reservationRepository;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected OrderRepository orderRepository;
    @MockBean
    protected UserDiscountCardRepository userDiscountCardRepository;

    protected String signIn(Roles role) throws Exception{

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("example@gmail.com");
        userEntity.setPassword(passwordEncoder.encode("qwerty"));
        userEntity.setRoles(role);

        given(userRepository.findByEmail("example@gmail.com")).willReturn(Optional.of(userEntity));

        final String response = mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"example@gmail.com\",\n" +
                        "  \"password\" : \"qwerty\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return "Bearer " + objectMapper.readValue(response, SignInResponse.class).getToken();
    }
}
