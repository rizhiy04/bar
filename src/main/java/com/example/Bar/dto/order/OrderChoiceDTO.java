package com.example.Bar.dto.order;

import com.example.Bar.dto.menuItem.MenuItemDTO;
import lombok.Data;

@Data
public class OrderChoiceDTO {

    private MenuItemDTO menuItem;
    private Integer count;
}
