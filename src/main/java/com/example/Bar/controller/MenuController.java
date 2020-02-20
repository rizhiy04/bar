package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.MenuService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/menu")
@Api(value = "Menu system")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all menu", notes = "Use this method to view all menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get menu"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<MenuItemDTO> getMenu(){
        return menuService.getMenu();
    }

    @GetMapping("/{category}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View menu by category", notes = "Use this method to view menu by category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get menu by category"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<MenuItemDTO> getMenuByCategory(@ApiParam(value = "Menu category", required = true)
            @PathVariable("category") final String category){
        return menuService.getMenuByCategory(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new menu item", notes = "Use this method to add new menu item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully add new menu item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public TextResponse addNewMenuItem(@ApiParam(value = "Add menu item data", required = true)
            @Valid @RequestBody final AddNewMenuItemRequestDTO addNewMenuItemRequestDTO){
        return menuService.addMenuItem(addNewMenuItemRequestDTO);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete menu item", notes = "Use this method to delete menu item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete menu item"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public TextResponse deleteMenuItem(@ApiParam(value = "Menu item id", required = true)
            @PathVariable("productId") final Integer productId) throws NoSuchElementException {
        return menuService.deleteMenuItem(productId);
    }
}
