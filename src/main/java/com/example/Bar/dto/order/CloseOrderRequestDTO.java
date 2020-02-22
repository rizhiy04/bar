package com.example.Bar.dto.order;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CloseOrderRequestDTO {

    @NotNull(message = "tableNumber is null")
    private Integer tableNumber;

    @NotNull(message = "clientId is null")
    private Integer clientId;
}
