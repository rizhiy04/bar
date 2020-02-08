package com.example.Bar.controller;

import com.example.Bar.dto.CloseOrderRequestDTO;
import com.example.Bar.dto.MakeNewOrderRequestDTO;
import com.example.Bar.dto.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.service.WaiterService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/bar/waiter", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class WaiterController {

    private final WaiterService waiterService;


    @GetMapping("/reservation")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservation(){
        return waiterService.getReservation();
    }

    @GetMapping("/freeTables/{hours}")
    @ResponseStatus(HttpStatus.OK)
    public String getFreeTable(@PathVariable("hours")String hours){
        return waiterService.getFreeTable(hours);
    }

    @PostMapping("/makeOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse makeNewOrder(@RequestBody MakeNewOrderRequestDTO makeNewOrderRequestDTO){
        return waiterService.makeNewOrder(makeNewOrderRequestDTO);
    }

    @PostMapping("/closeOrder")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse closeOrder(@RequestBody CloseOrderRequestDTO closeOrderRequestDTO){
        return waiterService.closeOrder(closeOrderRequestDTO);
    }
}
