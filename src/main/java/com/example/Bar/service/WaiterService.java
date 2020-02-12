package com.example.Bar.service;

import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.entity.MenuItem;
import com.example.Bar.entity.Order;
import com.example.Bar.entity.Reservation;
import com.example.Bar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WaiterService {

    private final ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservation(){
        List<ReservationDTO> response = new ArrayList<>();

        for (Reservation reservation : reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.now())){
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setId(reservation.getId());
            reservationDTO.setClientName(reservation.getName());
            reservationDTO.setReserveTime(reservation.getTime());
            reservationDTO.setTableNumber(reservation.getTableNumber());

            response.add(reservationDTO);
        }

        return response;
    }

    //TODO
    public FreeTablesDTO getFreeTable(final String hours){
        return null;
    }

    //TODO
    public TextResponse makeNewOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO){
        Order order = new Order();
        order.setTableNumber(makeNewOrderRequestDTO.getTableNumber());
        order.setTimeOpen(LocalDateTime.now());

        return null;
    }

    //TODO
    public TextResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO){
        return null;
    }

}
