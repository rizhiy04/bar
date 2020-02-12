package com.example.Bar.service;

import com.example.Bar.dto.*;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.entity.Event;
import com.example.Bar.entity.Inventory;
import com.example.Bar.entity.MenuItem;
import com.example.Bar.repository.EventRepository;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final InventoryRepository inventoryRepository;
    private final EventRepository eventRepository;
    private final MenuItemRepository menuItemRepository;


    public List<InventoryDTO> getInventory(){
        List<InventoryDTO> response = new ArrayList<>();

        for (Inventory inventory : inventoryRepository.findAll()){
            InventoryDTO inventoryDTO = new InventoryDTO(inventory.getId(), inventory.getName(), inventory.getCategory(), inventory.getCount());
            response.add(inventoryDTO);
        }

        return response;
    }

    //TODO will it work?
    public TextResponse changeInventoryCount(final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO){
        inventoryRepository.findById(changeInventoryCountRequestDTO.getId()).ifPresent(inventory -> inventory.setCount(changeInventoryCountRequestDTO.getCount()));

        return new TextResponse("Сохранено");
    }

    public TextResponse addNewInventory(final AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        Inventory inventory = new Inventory();
        inventory.setName(addNewInventoryRequestDTO.getName());
        inventory.setCategory(addNewInventoryRequestDTO.getCategory());
        inventory.setCount(addNewInventoryRequestDTO.getCount());

        inventoryRepository.save(inventory);

        return new TextResponse("Инвентарь добавлен");
    }

    public TextResponse deleteInventory(final Integer inventoryId){
        inventoryRepository.findById(inventoryId).ifPresent(inventory -> inventoryRepository.delete(inventory));

        return new TextResponse("Инвентарь удален");
    }

    public TextResponse addNewEvent(final AddNewEventRequestDTO addNewEventRequestDTO){
        Event event = new Event();
        event.setName(addNewEventRequestDTO.getEventName());
        event.setDescription(addNewEventRequestDTO.getDescription());
        event.setTime(addNewEventRequestDTO.getDate());

        eventRepository.save(event);

        return new TextResponse("Мероприятие добавлено");
    }

    public TextResponse deleteEvent(final Integer eventId){
        eventRepository.findById(eventId).ifPresent(event -> eventRepository.delete(event));

        return new TextResponse("Мероприятие удалено");
    }

    public TextResponse addNewMenuItem(final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        MenuItem menuItem = new MenuItem();
        menuItem.setName(addNewMenuItemRequestDTO.getName());
        menuItem.setCategory(addNewMenuItemRequestDTO.getCategory());
        menuItem.setDescription(addNewMenuItemRequestDTO.getDescription());
        menuItem.setPrice(addNewMenuItemRequestDTO.getPrice());

        menuItemRepository.save(menuItem);

        return new TextResponse("Позиция добавлена");
    }

    public TextResponse deleteMenuItem(final Integer productId){
        menuItemRepository.findById(productId).ifPresent(menuItem -> menuItemRepository.delete(menuItem));

        return new TextResponse("Позиция удалена");
    }

}
