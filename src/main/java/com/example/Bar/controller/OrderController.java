package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse makeNewOrder(@Valid @RequestBody final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException {
        return orderService.makeNewOrder(makeNewOrderRequestDTO);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TextResponse closeOrder(@Valid @RequestBody final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{
        return orderService.closeOrder(closeOrderRequestDTO);
    }
}
