package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> getEvents(){
        return eventService.getEvents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TextResponse addNewEvent(@Valid @RequestBody final AddNewEventRequestDTO addNewEventRequestDTO){
        return eventService.addNewEvent(addNewEventRequestDTO);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public TextResponse deleteEvent(@PathVariable("eventId") final Integer eventId) throws NoSuchElementException {
        return eventService.deleteEvent(eventId);
    }
}
