package com.example.Bar.dto;

import lombok.Data;

@Data
public class AddNewMenuItemRequestDTO {

    private String name;
    private String category;
    private String description;
    private Double price;
}
