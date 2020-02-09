package com.example.Bar.dto.menuItemDTO;

import lombok.Data;

@Data
public class AddNewMenuItemRequestDTO {

    private String name;
    private String category;
    private String description;
    private Double price;
}
