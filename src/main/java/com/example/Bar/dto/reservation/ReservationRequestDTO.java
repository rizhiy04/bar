package com.example.Bar.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {

    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime time;
}
