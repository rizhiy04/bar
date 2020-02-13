package com.example.Bar.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseOrderRequestDTO {

    @NotNull(message = "tableNumber is null")
    private Integer tableNumber;

    @NotNull(message = "clientId is null")
    private Integer clientId;
}
