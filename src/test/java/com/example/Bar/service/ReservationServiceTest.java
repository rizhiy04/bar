package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

class ReservationServiceTest extends AbstractServiceTest{

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testReservedTableDeny() throws Exception{
        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(createTables()));
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(Collections.singletonList(new ReservationEntity()));
        assertEquals(new TextResponse("Свободных мест на это время нет"), reservationService.reserveTable(createReservationRequestDTO()));
    }

    private InventoryEntity createTables(){
        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Table");
        inventoryEntity.setCategory("table");
        inventoryEntity.setAmount(1);

        return inventoryEntity;
    }

    private ReservationRequestDTO createReservationRequestDTO(){
        final ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setName("denis");
        reservationRequestDTO.setTime(LocalDateTime.now());

        return reservationRequestDTO;
    }
}