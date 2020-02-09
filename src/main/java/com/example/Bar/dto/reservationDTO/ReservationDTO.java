package com.example.Bar.dto.reservationDTO;

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
