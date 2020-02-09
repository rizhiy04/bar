package com.example.Bar.controller;

import com.example.Bar.dto.*;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
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
    public TextResponse changeInventoryCount(@RequestBody final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO){
        return adminService.changeInventoryCount(changeInventoryCountRequestDTO);
    }

    @PostMapping("/newInventory")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewInventory(@RequestBody final AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        return adminService.addNewInventory(addNewInventoryRequestDTO);
    }

    @DeleteMapping("/deleteInventory/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteInventory(@PathVariable("inventoryId") final Integer inventoryId){
        return adminService.deleteInventory(inventoryId);
    }

    @PostMapping("/addNewEvent")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewEvent(@RequestBody final AddNewEventRequestDTO addNewEventRequestDTO){
        return adminService.addNewEvent(addNewEventRequestDTO);
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteEvent(@PathVariable("eventId") final Integer eventId){
        return adminService.deleteEvent(eventId);
    }

    @PostMapping("/addNewMenuItem")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewMenuItem(@RequestBody final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        return adminService.addNewMenuItem(addNewMenuItemRequestDTO);
    }

    @DeleteMapping("/deleteMenuItem/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteMenuItem(@PathVariable("productId") final Integer productId){
        return adminService.deleteMenuItem(productId);
    }
}
