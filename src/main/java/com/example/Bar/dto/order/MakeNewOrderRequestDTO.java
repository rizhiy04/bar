package com.example.Bar.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeNewOrderRequestDTO {

    @NotNull(message = "tableNumber is null")
    private Integer tableNumber;

    @NotNull(message = "order is null")
    private List<Order> order;
}


