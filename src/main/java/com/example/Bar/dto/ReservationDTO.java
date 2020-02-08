package com.example.Bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReservationDTO {

    private Integer id;
    private String clientName;
    private String reserveTime;
    private Integer tableNumber;
}
