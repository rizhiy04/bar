package com.example.Bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventDTO {

    private Integer id;
    private String eventName;
    private String description;
    private String date;
}
