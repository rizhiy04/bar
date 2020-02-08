package com.example.Bar.service;

import com.example.Bar.dto.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {

    public List<InventoryDTO> getInventory(){
        return Arrays.asList(new InventoryDTO(1, "Рюмка 50 мл", "glass", 23));
    }

    public TextResponse changeInventoryCount(ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO){
        return new TextResponse("Сохранено");
    }

    public TextResponse addNewInventory(AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        return new TextResponse("Инвентарь добавлен");
    }

    public TextResponse deleteInventory(Integer inventoryId){
        return new TextResponse("Инвентарь удален");
    }

    public TextResponse addNewEvent(AddNewEventRequestDTO addNewEventRequestDTO){
        return new TextResponse("Мероприятие добавлено");
    }

    public TextResponse deleteEvent(Integer eventId){
        return new TextResponse("Мероприятие удалено");
    }

    public TextResponse addNewMenuItem(AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        return new TextResponse("Позиция добавлена");
    }

    public TextResponse deleteMenuItem(Integer productId){
        return new TextResponse("Позиция удалена");
    }

}
