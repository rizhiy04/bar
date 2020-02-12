package com.example.Bar.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewInventoryRequestDTO {

    private String name;
    private String category;
    private Integer count;
}
