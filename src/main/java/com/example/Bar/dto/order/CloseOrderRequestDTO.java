package com.example.Bar.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseOrderRequestDTO {

    private Integer tableNumber;
    private Integer clientId;
}
