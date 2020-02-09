package com.example.Bar.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
public class EventDTO {

    private Integer id;
    private String eventName;
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}
