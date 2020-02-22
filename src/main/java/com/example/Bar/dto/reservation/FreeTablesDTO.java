package com.example.Bar.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FreeTablesDTO {

    private List<Integer> tableNumbers;
}
