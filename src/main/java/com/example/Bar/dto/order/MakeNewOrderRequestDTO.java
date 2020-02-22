package com.example.Bar.dto.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MakeNewOrderRequestDTO {

    @NotNull(message = "tableNumber is null")
    private Integer tableNumber;

    @NotNull(message = "order is null")
    private List<Order> order;
}


