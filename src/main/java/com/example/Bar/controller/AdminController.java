package com.example.Bar.controller;

import com.example.Bar.dto.*;
import com.example.Bar.dto.eventDTO.AddNewEventRequestDTO;
import com.example.Bar.dto.inventoryDTO.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventoryDTO.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventoryDTO.InventoryDTO;
import com.example.Bar.dto.menuItemDTO.AddNewMenuItemRequestDTO;
import com.example.Bar.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/inventoryCount")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDTO> getInventory(){
        return adminService.getInventory();
    }

    @PostMapping("/inventoryCount")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse changeInventoryCount(@RequestBody ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO){
        return adminService.changeInventoryCount(changeInventoryCountRequestDTO);
    }

    @PostMapping("/newInventory")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewInventory(@RequestBody AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        return adminService.addNewInventory(addNewInventoryRequestDTO);
    }

    @DeleteMapping("/deleteInventory/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteInventory(@PathVariable("inventoryId")Integer inventoryId){
        return adminService.deleteInventory(inventoryId);
    }

    @PostMapping("/addNewEvent")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewEvent(@RequestBody AddNewEventRequestDTO addNewEventRequestDTO){
        return adminService.addNewEvent(addNewEventRequestDTO);
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteEvent(@PathVariable("eventId")Integer eventId){
        return adminService.deleteEvent(eventId);
    }

    @PostMapping("/addNewMenuItem")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewMenuItem(@RequestBody AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        return adminService.addNewMenuItem(addNewMenuItemRequestDTO);
    }

    @DeleteMapping("/deleteMenuItem/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteMenuItem(@PathVariable("productId")Integer productId){
        return adminService.deleteMenuItem(productId);
    }
}
