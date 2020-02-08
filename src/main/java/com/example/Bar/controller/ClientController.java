package com.example.Bar.controller;

import com.example.Bar.dto.EventDTO;
import com.example.Bar.dto.MenuItemDTO;
import com.example.Bar.dto.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.service.ClientService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/bar", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
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
