package com.example.Bar.service;

import com.example.Bar.dto.CloseOrderRequestDTO;
import com.example.Bar.dto.MakeNewOrderRequestDTO;
import com.example.Bar.dto.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WaiterService {

    public List<ReservationDTO> getReservation(){
        return Arrays.asList(new ReservationDTO(1, "Денис", "04.03.2020 19:00", 2));
    }

    public String getFreeTable(String hours){
        return "[\n"+
                "{\n" +
                "  \"tableNumber\" : 1\n" +
                "}\n" +
                "]";
    }

    public TextResponse makeNewOrder(MakeNewOrderRequestDTO makeNewOrderRequestDTO){
        return new TextResponse("Заказ оформлен");
    }

    public TextResponse closeOrder(CloseOrderRequestDTO closeOrderRequestDTO){
        return new TextResponse("Заказ закрыт, к оплате 25р");
    }

}