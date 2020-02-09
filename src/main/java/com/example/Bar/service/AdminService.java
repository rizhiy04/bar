package com.example.Bar.service;

import com.example.Bar.dto.*;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AdminService {

    public List<InventoryDTO> getInventory(){
        return Collections.singletonList(new InventoryDTO(1, "Рюмка 50 мл", "glass", 23));
    }

    public TextResponse changeInventoryCount(final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO){
        return new TextResponse("Сохранено");
    }

    public TextResponse addNewInventory(final AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        return new TextResponse("Инвентарь добавлен");
    }

    public TextResponse deleteInventory(final Integer inventoryId){
        return new TextResponse("Инвентарь удален");
    }

    public TextResponse addNewEvent(final AddNewEventRequestDTO addNewEventRequestDTO){
        return new TextResponse("Мероприятие добавлено");
    }

    public TextResponse deleteEvent(final Integer eventId){
        return new TextResponse("Мероприятие удалено");
    }

    public TextResponse addNewMenuItem(final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        return new TextResponse("Позиция добавлена");
    }

    public TextResponse deleteMenuItem(final Integer productId){
        return new TextResponse("Позиция удалена");
    }

}
