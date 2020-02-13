package com.example.Bar.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {

    @NotBlank(message = "name is blank")
    private String name;

    @NotNull(message = "time is null")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime time;
}
