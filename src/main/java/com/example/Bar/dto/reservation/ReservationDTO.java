package com.example.Bar.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    private Integer id;
    private String clientName;
    private Integer tableNumber;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime reserveTime;


}
