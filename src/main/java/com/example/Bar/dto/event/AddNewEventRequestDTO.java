package com.example.Bar.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddNewEventRequestDTO {

    private String eventName;
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}
