package com.example.Bar.controller;

import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.WaiterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/waiter")
public class WaiterController {

    private final WaiterService waiterService;


    @GetMapping("/reservation")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservation(){
        return waiterService.getReservation();
    }

    @GetMapping("/freeTables/{hours}")
    @ResponseStatus(HttpStatus.OK)
    public FreeTablesDTO getFreeTable(@PathVariable("hours") final Integer hours) throws NoSuchElementException {
        return waiterService.getFreeTable(hours);
    }

    @PostMapping("/makeOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse makeNewOrder(@Valid @RequestBody final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException{
        return waiterService.makeNewOrder(makeNewOrderRequestDTO);
    }

    @PostMapping("/closeOrder")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse closeOrder(@Valid @RequestBody final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{
        return waiterService.closeOrder(closeOrderRequestDTO);
    }
}
