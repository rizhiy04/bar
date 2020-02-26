package com.example.Bar.controller;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.exception.BarNoSuchElementException;
import com.example.Bar.service.EventService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
@Api(value = "Events system")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all events", notes = "Use this method to view all events")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get events"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<EventDTO> getEvents(){
        return eventService.getEvents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new event", notes = "Use this method, if you want to add new event")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully add new event"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void addEvent(@ApiParam(value = "New event data", required = true)
            @Valid @RequestBody final AddNewEventRequestDTO addNewEventRequestDTO){
        eventService.addEvent(addNewEventRequestDTO);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete event", notes = "Use this method, if you want to delete event")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete event"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteEvent(@PathVariable("eventId") final Integer eventId) throws BarNoSuchElementException {
        eventService.deleteEvent(eventId);
    }
}
