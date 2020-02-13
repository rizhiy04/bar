package com.example.Bar.service;

import com.example.Bar.dto.*;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.entity.EventEntity;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.EventRepository;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {

    private final InventoryRepository inventoryRepository;
    private final EventRepository eventRepository;
    private final MenuItemRepository menuItemRepository;


    public List<InventoryDTO> getInventory(){
        return inventoryRepository.findAll().stream().map(
                inventoryEntity -> new InventoryDTO(inventoryEntity.getId(), inventoryEntity.getName(), inventoryEntity.getCategory(), inventoryEntity.getCount()))
                .collect(Collectors.toList());
    }

    public TextResponse changeInventoryCount(final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO) throws NoSuchElementException{
        InventoryEntity inventoryEntity = inventoryRepository.findById(changeInventoryCountRequestDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Such inventoryEntity doesn't exist"));

        inventoryEntity.setCount(changeInventoryCountRequestDTO.getCount());
        inventoryRepository.save(inventoryEntity);

        return new TextResponse("Сохранено");
    }

    public TextResponse addNewInventory(final AddNewInventoryRequestDTO addNewInventoryRequestDTO){

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setName(addNewInventoryRequestDTO.getName());
        inventoryEntity.setCategory(addNewInventoryRequestDTO.getCategory());
        inventoryEntity.setCount(addNewInventoryRequestDTO.getCount());

        inventoryRepository.save(inventoryEntity);

        return new TextResponse("Инвентарь добавлен");
    }

    public TextResponse deleteInventory(final Integer inventoryId) throws NoSuchElementException{
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new NoSuchElementException("Such inventoryEntity doesn't exist"));

        inventoryRepository.delete(inventoryEntity);

        return new TextResponse("Инвентарь удален");
    }

    public TextResponse addNewEvent(final AddNewEventRequestDTO addNewEventRequestDTO){

        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(addNewEventRequestDTO.getEventName());
        eventEntity.setDescription(addNewEventRequestDTO.getDescription());
        eventEntity.setTime(addNewEventRequestDTO.getDate());

        eventRepository.save(eventEntity);

        return new TextResponse("Мероприятие добавлено");
    }

    public TextResponse deleteEvent(final Integer eventId) throws NoSuchElementException{
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Such eventEntity doesn't exist"));

        eventRepository.delete(eventEntity);

        return new TextResponse("Мероприятие удалено");
    }

    public TextResponse addNewMenuItem(final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setName(addNewMenuItemRequestDTO.getName());
        menuItemEntity.setCategory(addNewMenuItemRequestDTO.getCategory());
        menuItemEntity.setDescription(addNewMenuItemRequestDTO.getDescription());
        menuItemEntity.setPrice(addNewMenuItemRequestDTO.getPrice());

        menuItemRepository.save(menuItemEntity);

        return new TextResponse("Позиция добавлена");
    }

    public TextResponse deleteMenuItem(final Integer productId) throws NoSuchElementException{
        MenuItemEntity menuItemEntity = menuItemRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist"));

        menuItemRepository.delete(menuItemEntity);

        return new TextResponse("Позиция удалена");
    }

}
