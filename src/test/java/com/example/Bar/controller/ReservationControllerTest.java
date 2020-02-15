package com.example.Bar.controller;

import com.example.Bar.repository.ReservationRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReservationControllerTest extends AbstractControllerTest{

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testReserveTableIsCreated() throws Exception{

        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"time\" : \"04-03-2020 19:00\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Ваш столик №1\"\n" +
                        "}"));

        reservationRepository.delete(reservationRepository.findAll().get(1));
    }

    @Test
    public void testGetReservationIsOk() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(get("/reservation").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"clientName\" : \"Денис\",\n" +
                        "  \"reserveTime\" : \"04-03-2020 19:00\",\n" +
                        "  \"tableNumber\" : 2\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testGetReservationAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/reservation").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetFreeTablesIsOk() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(get("/reservation/free/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tableNumbers\" : [1, 3]\n" +
                        "}\n"));
    }

    @Test
    public void testGetFreeTablesAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/reservation/free/2").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

}