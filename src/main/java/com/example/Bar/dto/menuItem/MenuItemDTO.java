package com.example.Bar.dto.menuItem;

import lombok.Data;

@Data
public class MenuItemDTO {

    private Integer id;
    private String name;
    private String category;
    private String description;
    private Double price;
}
