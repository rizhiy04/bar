package com.example.Bar.converter;

import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.entity.MenuItemEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MenuItemConverter {

    public MenuItemDTO convertToDTO(final MenuItemEntity menuItemEntity){
        final MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setId(menuItemEntity.getId());
        menuItemDTO.setName(menuItemEntity.getName());
        menuItemDTO.setCategory(menuItemEntity.getCategory());
        menuItemDTO.setDescription(menuItemEntity.getDescription());
        menuItemDTO.setPrice(menuItemEntity.getPrice().doubleValue());

        return menuItemDTO;
    }

    public MenuItemEntity convertToEntity(final AddNewMenuItemRequestDTO menuItemDTO){
        final MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setName(menuItemDTO.getName());
        menuItemEntity.setCategory(menuItemDTO.getCategory());
        menuItemEntity.setDescription(menuItemDTO.getDescription());
        menuItemEntity.setPrice(BigDecimal.valueOf(menuItemDTO.getPrice()));

        return menuItemEntity;
    }
}
