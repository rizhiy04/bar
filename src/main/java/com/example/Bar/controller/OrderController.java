package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.order.OrderDTO;
import com.example.Bar.dto.order.RevenueRequest;
import com.example.Bar.exception.BarNoSuchElementException;
import com.example.Bar.service.OrderService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
@Api(value = "Orders system")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View orders", notes = "Use this method, if you want to view orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get orders"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<OrderDTO> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping("/revenue")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get revenue by date", notes = "Use this method, if you want to get revenue by date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get revenue"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public TextResponse getRevenueByTime(@ApiParam(value = "Revenue data")
            @Valid @RequestBody final RevenueRequest revenueRequest){
        return orderService.getRevenueByTime(revenueRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create order", notes = "Use this method, if you want to create order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create order"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void makeOrder(@ApiParam(value = "New order data")
            @Valid @RequestBody final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws BarNoSuchElementException {
        orderService.makeOrder(makeNewOrderRequestDTO);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Close order", notes = "Use this method, if you want to close order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully close order"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public TextResponse closeOrder(@ApiParam(value = "Close order data", required = true)
            @Valid @RequestBody final CloseOrderRequestDTO closeOrderRequestDTO) throws BarNoSuchElementException {
        return orderService.closeOrder(closeOrderRequestDTO);
    }
}
