package com.example.Bar.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AddNewEventRequestDTO {

    @NotBlank(message = "eventName is blank")
    private String eventName;

    @NotBlank(message = "description is blank")
    private String description;

    @NotNull(message = "date is null")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}
