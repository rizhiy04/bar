package com.example.Bar.dto.orderDTO;

import lombok.Data;

import java.util.List;

@Data
public class MakeNewOrderRequestDTO {

    private Integer tableNumber;
    private List<Order> order;
}


