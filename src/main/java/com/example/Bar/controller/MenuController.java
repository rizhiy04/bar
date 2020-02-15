package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemDTO> getMenu(){
        return menuService.getMenu();
    }

    @GetMapping("/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemDTO> getMenuByCategory(@PathVariable("category") final String category){
        return menuService.getMenuByCategory(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewMenuItem(@Valid @RequestBody final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        return menuService.addMenuItem(addNewMenuItemRequestDTO);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteMenuItem(@PathVariable("productId") final Integer productId) throws NoSuchElementException {
        return menuService.deleteMenuItem(productId);
    }
}
