package com.example.Bar.dto;

import lombok.Data;

import java.util.List;

@Data
public class MakeNewOrderRequestDTO {

    private Integer id;
    private List<Order> order;

}

@Data
class Order {

    private Integer id;
    private Integer count;
}


