package com.example.Bar.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewInventoryRequestDTO {

    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "category is blank")
    private String category;

    @NotNull(message = "count is null")
    private Integer count;
}
