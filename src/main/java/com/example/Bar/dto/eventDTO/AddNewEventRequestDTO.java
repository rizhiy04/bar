package com.example.Bar.dto.eventDTO;

import lombok.Data;

@Data
public class AddNewEventRequestDTO {

    private String eventName;
    private String description;
    private String date;
}
