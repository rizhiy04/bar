package com.example.Bar.dto.inventoryDTO;

import lombok.Data;

@Data
public class AddNewInventoryRequestDTO {

    private String name;
    private String category;
    private Integer count;
}
