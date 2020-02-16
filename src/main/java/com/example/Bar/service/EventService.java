package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.entity.EventEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;

    public List<EventDTO> getEvents(){
        return eventRepository.findAllByTimeAfterOrderByIdAsc(LocalDateTime.now()).stream().map(
                event -> new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getTime()))
                .collect(Collectors.toList());
    }

    public TextResponse addEvent(final AddNewEventRequestDTO addNewEventRequestDTO){
        eventRepository.save(convertDtoToEntity(addNewEventRequestDTO));

        return new TextResponse("Мероприятие добавлено");
    }

    public TextResponse deleteEvent(final Integer eventId) throws NoSuchElementException {
        final EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Such eventEntity doesn't exist"));

        eventRepository.delete(eventEntity);

        return new TextResponse("Мероприятие удалено");
    }

    private EventEntity convertDtoToEntity(final AddNewEventRequestDTO addNewEventRequestDTO){
        final EventEntity eventEntity = new EventEntity();
        eventEntity.setName(addNewEventRequestDTO.getEventName());
        eventEntity.setDescription(addNewEventRequestDTO.getDescription());
        eventEntity.setTime(addNewEventRequestDTO.getDate());

        return eventEntity;
    }

}
