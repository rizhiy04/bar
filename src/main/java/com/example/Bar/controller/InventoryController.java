package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.inventory.AddNewInventoryRequestDTO;
import com.example.Bar.dto.inventory.ChangeInventoryCountRequestDTO;
import com.example.Bar.dto.inventory.InventoryDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDTO> getInventory(){
        return inventoryService.getInventory();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TextResponse changeInventoryCount(@Valid @RequestBody final ChangeInventoryCountRequestDTO changeInventoryCountRequestDTO) throws NoSuchElementException {
        return inventoryService.changeInventoryCount(changeInventoryCountRequestDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewInventory(@Valid @RequestBody final AddNewInventoryRequestDTO addNewInventoryRequestDTO){
        return inventoryService.addNewInventory(addNewInventoryRequestDTO);
    }

    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteInventory(@PathVariable("inventoryId") final Integer inventoryId) throws NoSuchElementException{
        return inventoryService.deleteInventory(inventoryId);
    }
}
