package com.example.Bar.converter;

import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.entity.InventoryEntity;
import org.springframework.stereotype.Component;

@Component
public class InventoryConverter {

    public InventoryDTO convertToDTO(final InventoryEntity inventoryEntity){
        final InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventoryEntity.getId());
        inventoryDTO.setName(inventoryEntity.getName());
        inventoryDTO.setCategory(inventoryEntity.getCategory());
        inventoryDTO.setCount(inventoryEntity.getAmount());

        return inventoryDTO;
    }

    public InventoryEntity convertToEntity(final AddNewInventoryRequestDTO inventoryDTO){
        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setName(inventoryDTO.getName());
        inventoryEntity.setCategory(inventoryDTO.getCategory());
        inventoryEntity.setAmount(inventoryDTO.getCount());

        return inventoryEntity;
    }
}
