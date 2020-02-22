package com.example.Bar.dto.inventory;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddNewInventoryRequestDTO {

    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "category is blank")
    private String category;

    @NotNull(message = "count is null")
    private Integer count;
}
