package com.example.Bar.controller;

import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReservationControllerTest extends AbstractControllerTest{

    @Test
    public void testReserveTableIsCreated() throws Exception{

        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(100);
        inventoryEntity.setName("Стол");
        inventoryEntity.setCategory("table");
        inventoryEntity.setCount(5);

        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(inventoryEntity));

        final ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1000);
        reservationEntity.setName("Qwer");
        reservationEntity.setTime(LocalDateTime.of(2020, 3,4,17,0));
        reservationEntity.setTableNumber(1);

        final LocalDateTime reserveTime = LocalDateTime.of(2020, 3,4,19,0);
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(reserveTime.minusHours(3), reserveTime.plusHours(3))).willReturn(Collections.singletonList(reservationEntity));

        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"time\" : \"04-03-2020 19:00\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Ваш столик №2\"\n" +
                        "}"));
    }

    @Test
    public void testGetReservationIsOk() throws Exception{
        final String token = signIn(Roles.WAITER);

        final ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1);
        reservationEntity.setName("Денис");
        reservationEntity.setTableNumber(2);
        reservationEntity.setTime(LocalDateTime.of(2020, 3,4,19,0));

        LocalDateTime now = LocalDateTime.now();
        given(reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute()))).willReturn(Collections.singletonList(reservationEntity));

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

        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(100);
        inventoryEntity.setName("Стол");
        inventoryEntity.setCategory("table");
        inventoryEntity.setCount(3);

        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(inventoryEntity));

        final LocalDateTime now = LocalDateTime.now();
        final ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1000);
        reservationEntity.setName("Qwer");
        reservationEntity.setTime(now.plusHours(1));
        reservationEntity.setTableNumber(2);

        given(reservationRepository.findAllByTimeAfterAndTimeBefore(any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(Collections.singletonList(reservationEntity));

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