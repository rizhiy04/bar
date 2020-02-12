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
import com.example.Bar.repository.EventRepository;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    //TODO change exception
    public TextResponse changeInventoryCount(final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO) throws Exception{
        Optional<InventoryEntity> optionalInventory = inventoryRepository.findById(changeInventoryCountRequestDTO.getId());

        if (!optionalInventory.isPresent()){
            throw new Exception("No such element");
        }

        optionalInventory.get().setCount(changeInventoryCountRequestDTO.getCount());
        inventoryRepository.save(optionalInventory.get());

        return new TextResponse("Сохранено");
    }

    //TODO change exception
    public TextResponse addNewInventory(final AddNewInventoryRequestDTO addNewInventoryRequestDTO) throws Exception{

        if (addNewInventoryRequestDTO.getName() == null || addNewInventoryRequestDTO.getCategory() == null || addNewInventoryRequestDTO.getCount() == null){
            throw new Exception("Bad Request");
        }

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setName(addNewInventoryRequestDTO.getName());
        inventoryEntity.setCategory(addNewInventoryRequestDTO.getCategory());
        inventoryEntity.setCount(addNewInventoryRequestDTO.getCount());

        inventoryRepository.save(inventoryEntity);

        return new TextResponse("Инвентарь добавлен");
    }

    //TODO change exception
    public TextResponse deleteInventory(final Integer inventoryId) throws Exception{
        Optional<InventoryEntity> optionalInventory = inventoryRepository.findById(inventoryId);

        if (!optionalInventory.isPresent()){
            throw new Exception("No such element");
        }

        inventoryRepository.delete(optionalInventory.get());

        return new TextResponse("Инвентарь удален");
    }

    //TODO change exception
    public TextResponse addNewEvent(final AddNewEventRequestDTO addNewEventRequestDTO) throws Exception{

        if (addNewEventRequestDTO.getEventName() == null || addNewEventRequestDTO.getDescription() == null || addNewEventRequestDTO.getDate() == null){
            throw new Exception("Bad Request");
        }

        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(addNewEventRequestDTO.getEventName());
        eventEntity.setDescription(addNewEventRequestDTO.getDescription());
        eventEntity.setTime(addNewEventRequestDTO.getDate());

        eventRepository.save(eventEntity);

        return new TextResponse("Мероприятие добавлено");
    }

    //TODO change exception
    public TextResponse deleteEvent(final Integer eventId) throws Exception{
        Optional<EventEntity> optionalEvent = eventRepository.findById(eventId);

        if (!optionalEvent.isPresent()){
            throw new Exception("No such element");
        }

        eventRepository.delete(optionalEvent.get());

        return new TextResponse("Мероприятие удалено");
    }

    //TODO change exception
    public TextResponse addNewMenuItem(final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO) throws Exception{

        if (addNewMenuItemRequestDTO.getName() == null || addNewMenuItemRequestDTO.getCategory() == null || addNewMenuItemRequestDTO.getDescription() == null|| addNewMenuItemRequestDTO.getPrice() == null){
            throw new Exception("Bad Request");
        }

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setName(addNewMenuItemRequestDTO.getName());
        menuItemEntity.setCategory(addNewMenuItemRequestDTO.getCategory());
        menuItemEntity.setDescription(addNewMenuItemRequestDTO.getDescription());
        menuItemEntity.setPrice(addNewMenuItemRequestDTO.getPrice());

        menuItemRepository.save(menuItemEntity);

        return new TextResponse("Позиция добавлена");
    }

    //TODO change exception
    public TextResponse deleteMenuItem(final Integer productId) throws Exception{
        Optional<MenuItemEntity> optionalMenuItem = menuItemRepository.findById(productId);

        if (!optionalMenuItem.isPresent()){
            throw new Exception("No such element");
        }

        menuItemRepository.delete(optionalMenuItem.get());

        return new TextResponse("Позиция удалена");
    }

}
