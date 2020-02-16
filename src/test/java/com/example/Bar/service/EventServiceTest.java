package com.example.Bar.service;

import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class EventServiceTest extends AbstractServiceTest{

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @Test
    public void deleteEventThrowException(){
        given(eventRepository.findById(1)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->
            eventService.deleteEvent(1));
    }
}