package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ReservationServiceTest extends AbstractServiceTest{

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;
    @MockBean
    private InventoryRepository inventoryRepository;

    @Test
    public void reservedTableDeny() throws Exception{
        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(createInventoryEntity()));
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(Collections.singletonList(new ReservationEntity()));
        assertEquals(new TextResponse("Свободных мест на это время нет"), reservationService.reserveTable(createReservationRequestDTO()));
    }

    private InventoryEntity createInventoryEntity(){
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