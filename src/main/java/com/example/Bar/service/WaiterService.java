package com.example.Bar.service;

import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaiterService {

    private final ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservation(){
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute())).stream().map(
                reservation -> new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getTableNumber(), reservation.getTime()))
                .collect(Collectors.toList());
    }

    //TODO
    public FreeTablesDTO getFreeTable(final String hours){
        return null;
    }

    //TODO
    public TextResponse makeNewOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableNumber(makeNewOrderRequestDTO.getTableNumber());
        orderEntity.setTimeOpen(LocalDateTime.now());

        return null;
    }

    //TODO
    public TextResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO){
        return null;
    }

}
