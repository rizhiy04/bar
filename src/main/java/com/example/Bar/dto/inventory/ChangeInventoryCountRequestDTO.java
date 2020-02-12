package com.example.Bar.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInventoryCountRequestDTO {

    private Integer id;
    private Integer count;
}
