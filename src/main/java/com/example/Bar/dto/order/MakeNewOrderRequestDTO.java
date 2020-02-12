package com.example.Bar.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeNewOrderRequestDTO {

    private Integer tableNumber;
    private List<Order> order;
}


