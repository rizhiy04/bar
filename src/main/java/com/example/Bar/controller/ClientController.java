package com.example.Bar.controller;

import com.example.Bar.dto.eventDTO.EventDTO;
import com.example.Bar.dto.menuItemDTO.MenuItemDTO;
import com.example.Bar.dto.reservationDTO.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class ClientController {

    private final ClientService clientService;


    @GetMapping("/menu")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemDTO> getMenu(){
        return clientService.getMenu();
    }

    @GetMapping("/menu/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemDTO> getMenuByCategory(@PathVariable("category") String category){
        return clientService.getMenuByCategory(category);
    }

    @PostMapping("/reserveTable")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse reserveTable(@RequestBody ReservationRequestDTO reserve){
        return clientService.reserveTable(reserve);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getEvents(){
        return clientService.getEvents();
    }
}
