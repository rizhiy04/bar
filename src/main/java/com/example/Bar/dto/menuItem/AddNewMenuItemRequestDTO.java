package com.example.Bar.dto.menuItem;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddNewMenuItemRequestDTO {

    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "category is blank")
    private String category;

    @NotBlank(message = "description is blank")
    private String description;

    @NotNull(message = "price is null")
    private Double price;
}
