package com.example.Bar.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Integer id;
    private Integer tableNumber;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timeOpen;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timeClose;
    private List<OrderChoiceDTO> order;
}
