package com.example.Bar.converter;

import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventConverter {

    public EventDTO convertToDTO(final EventEntity event){
        final EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventName(event.getName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDate(event.getTime());

        return eventDTO;
    }

    public EventEntity convertToEntity(final AddNewEventRequestDTO eventDTO){
        final EventEntity eventEntity = new EventEntity();
        eventEntity.setName(eventDTO.getEventName());
        eventEntity.setDescription(eventDTO.getDescription());
        eventEntity.setTime(eventDTO.getDate());

        return eventEntity;
    }
}
