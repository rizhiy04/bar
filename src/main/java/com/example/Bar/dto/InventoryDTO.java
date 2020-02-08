package com.example.Bar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InventoryDTO {

    private Integer id;
    private String name;
    private String category;
    private Integer count;
}
