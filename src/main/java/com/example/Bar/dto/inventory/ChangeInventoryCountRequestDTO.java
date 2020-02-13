package com.example.Bar.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInventoryCountRequestDTO {

    @NotNull(message = "id is null")
    private Integer id;

    @NotNull(message = "count is null")
    private Integer count;
}
