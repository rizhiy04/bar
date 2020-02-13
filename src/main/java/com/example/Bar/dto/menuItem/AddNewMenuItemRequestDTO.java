package com.example.Bar.dto.menuItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
