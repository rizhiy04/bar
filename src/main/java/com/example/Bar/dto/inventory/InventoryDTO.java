package com.example.Bar.dto.inventory;

import lombok.Data;

@Data
public class InventoryDTO {

    private Integer id;
    private String name;
    private String category;
    private Integer count;
}
