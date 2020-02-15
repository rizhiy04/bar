package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryDTO> getInventories(){
        return inventoryRepository.findAll().stream().map(
                inventoryEntity -> new InventoryDTO(inventoryEntity.getId(), inventoryEntity.getName(), inventoryEntity.getCategory(), inventoryEntity.getCount()))
                .collect(Collectors.toList());
    }

    public TextResponse changeInventoryCount(final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO) throws NoSuchElementException {
        final InventoryEntity inventoryEntity = inventoryRepository.findById(changeInventoryCountRequestDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Such inventoryEntity doesn't exist"));

        inventoryEntity.setCount(changeInventoryCountRequestDTO.getCount());
        inventoryRepository.save(inventoryEntity);

        return new TextResponse("Сохранено");
    }

    public TextResponse addInventory(final AddNewInventoryRequestDTO addNewInventoryRequestDTO){

        final InventoryEntity inventoryEntity = new InventoryEntity();
        convertDtoToEntity(inventoryEntity, addNewInventoryRequestDTO);

        inventoryRepository.save(inventoryEntity);

        return new TextResponse("Инвентарь добавлен");
    }

    public TextResponse deleteInventory(final Integer inventoryId) throws NoSuchElementException{
        final InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new NoSuchElementException("Such inventoryEntity doesn't exist"));

        inventoryRepository.delete(inventoryEntity);

        return new TextResponse("Инвентарь удален");
    }

    private void convertDtoToEntity(final InventoryEntity inventoryEntity, final AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        inventoryEntity.setName(addNewInventoryRequestDTO.getName());
        inventoryEntity.setCategory(addNewInventoryRequestDTO.getCategory());
        inventoryEntity.setCount(addNewInventoryRequestDTO.getCount());
    }

}
