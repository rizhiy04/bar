package com.example.Bar.service;

import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class WaiterService {

    public List<ReservationDTO> getReservation(){
        return Collections.singletonList(new ReservationDTO(1, "Денис", 2, LocalDateTime.of(2020, 3, 4, 19,0)));
    }

    public FreeTablesDTO getFreeTable(final String hours){
        return new FreeTablesDTO(Arrays.asList(1,3));
    }

    public TextResponse makeNewOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO){
        return new TextResponse("Заказ оформлен");
    }

    public TextResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO){
        return new TextResponse("Заказ закрыт, к оплате 25р");
    }

}
