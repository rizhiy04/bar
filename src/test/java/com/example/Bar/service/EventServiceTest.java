package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.entity.EventEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @Test
    public void getEventsSuccessTest(){
        given(eventRepository.findAllByTimeAfterOrderByIdAsc(any(LocalDateTime.class))).willReturn(Collections.singletonList(createEventEntity()));
        assertEquals(Collections.singletonList(createEventDTO()), eventService.getEvents());
    }

    @Test
    public void addEventSuccessTest(){
        assertEquals(new TextResponse("Мероприятие добавлено"), eventService.addEvent(createAddNewEventRequestDTO()));
    }

    @Test
    public void deleteEventSuccess() throws Exception{
        given(eventRepository.findById(1)).willReturn(Optional.of(createEventEntity()));
        assertEquals(new TextResponse("Мероприятие удалено"), eventService.deleteEvent(1));
    }

    @Test
    public void deleteEventThrowException(){
        given(eventRepository.findById(1)).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            eventService.deleteEvent(1);
        });
    }

    private EventEntity createEventEntity(){
        final EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1);
        eventEntity.setName("Event");
        eventEntity.setDescription("qwerty");
        eventEntity.setTime(LocalDateTime.of(2020, 3,4,19,0));

        return eventEntity;
    }

    private EventDTO createEventDTO(){
        final EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1);
        eventDTO.setEventName("Event");
        eventDTO.setDescription("qwerty");
        eventDTO.setDate(LocalDateTime.of(2020, 3,4,19,0));

        return eventDTO;
    }

    private AddNewEventRequestDTO createAddNewEventRequestDTO(){
        final AddNewEventRequestDTO addNewEventRequestDTO = new AddNewEventRequestDTO();
        addNewEventRequestDTO.setEventName("event");
        addNewEventRequestDTO.setDescription("qwerty");
        addNewEventRequestDTO.setDate(LocalDateTime.now());

        return addNewEventRequestDTO;
    }

}