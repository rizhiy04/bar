package com.example.Bar.service;

import com.example.Bar.dto.orderDTO.CloseOrderRequestDTO;
import com.example.Bar.dto.orderDTO.MakeNewOrderRequestDTO;
import com.example.Bar.dto.reservationDTO.FreeTablesDTO;
import com.example.Bar.dto.reservationDTO.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class WaiterService {

    public List<ReservationDTO> getReservation(){
        return Collections.singletonList(new ReservationDTO(1, "Денис","04.03.2020 19:00", 2));
    }

    public FreeTablesDTO getFreeTable(String hours){
        return new FreeTablesDTO(Arrays.asList(1,3));
    }

    public TextResponse makeNewOrder(MakeNewOrderRequestDTO makeNewOrderRequestDTO){
        return new TextResponse("Заказ оформлен");
    }

    public TextResponse closeOrder(CloseOrderRequestDTO closeOrderRequestDTO){
        return new TextResponse("Заказ закрыт, к оплате 25р");
    }

}
