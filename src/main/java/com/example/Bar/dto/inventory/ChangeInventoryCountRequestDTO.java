package com.example.Bar.dto.inventory;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChangeInventoryCountRequestDTO {

    @NotNull(message = "id is null")
    private Integer id;

    @NotNull(message = "count is null")
    private Integer count;
}
