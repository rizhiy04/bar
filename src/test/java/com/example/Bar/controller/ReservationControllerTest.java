package com.example.Bar.controller;

import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationControllerTest extends AbstractControllerTest{

    @Test
    public void testReserveTableIsCreated() throws Exception{
        //given
        final ReservationEntity testReservationEntity = getReservationEntity();
        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(createTables()));
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(Collections.singletonList(testReservationEntity));

        //when
        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"time\" : \"04-03-2020 19:00\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"№1\"\n" +
                        "}"));

        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));
    }

    @Test
    public void testGetReservationsIsOk() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);
        final ReservationEntity testReservationEntity = getReservationEntity();
        given(reservationRepository.findAllByTimeAfterOrderById(any(LocalDateTime.class)))
                .willReturn(Collections.singletonList(testReservationEntity));

        //when
        mockMvc.perform(get("/reservations").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : "+ testReservationEntity.getId() +",\n" +
                        "  \"clientName\" : \"Денис\",\n" +
                        "  \"reserveTime\" : \"04-03-2020 19:00\",\n" +
                        "  \"tableNumber\" : 2\n" +
                        "}\n" +
                        "]"));

        verify(reservationRepository, times(1)).findAllByTimeAfterOrderById(any(LocalDateTime.class));
    }

    @Test
    public void testGetReservationAccessDeniedForClient() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(get("/reservations").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetFreeTablesIsOk() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);
        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(createTables()));
        given(orderRepository.findAllByTimeCloseIsNull()).willReturn(new ArrayList<>());
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(Collections.singletonList(getReservationEntity()));

        //when
        mockMvc.perform(get("/reservations/free/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tableNumbers\" : [1, 3]\n" +
                        "}\n"));
    }

    @Test
    public void testGetFreeTablesAccessDeniedForClient() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(get("/reservations/free/2").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    private ReservationEntity getReservationEntity(){
        final ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1);
        reservationEntity.setName("Денис");
        reservationEntity.setTableNumber(2);
        reservationEntity.setTime(LocalDateTime.of(2020,3,4,19,0));

        return reservationEntity;
    }

    private InventoryEntity createTables(){
        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Table");
        inventoryEntity.setCategory("table");
        inventoryEntity.setAmount(3);

        return inventoryEntity;
    }

}