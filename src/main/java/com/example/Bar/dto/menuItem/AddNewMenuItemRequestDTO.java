package com.example.Bar.dto.menuItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewMenuItemRequestDTO {

    private String name;
    private String category;
    private String description;
    private Double price;
}
