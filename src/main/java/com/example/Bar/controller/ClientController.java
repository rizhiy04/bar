package com.example.Bar.controller;

import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<MenuItemDTO> getMenuByCategory(@PathVariable("category") final String category){
        return clientService.getMenuByCategory(category);
    }

    @PostMapping("/reserveTable")
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse reserveTable(@Valid @RequestBody final ReservationRequestDTO reserve) throws NoSuchElementException {
        return clientService.reserveTable(reserve);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getEvents(){
        return clientService.getEvents();
    }
}
