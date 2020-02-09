package com.example.Bar.dto.reservationDTO;

import lombok.Data;

import java.util.List;

@Data
public class FreeTablesDTO {

    private final List<Integer> tableNumbers;
}
