package com.example.Bar.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class MakeNewOrderRequestDTO {

    private Integer tableNumber;
    private List<Order> order;
}


