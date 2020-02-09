package com.example.Bar.dto.inventory;

import lombok.Data;

@Data
public class AddNewInventoryRequestDTO {

    private String name;
    private String category;
    private Integer count;
}
