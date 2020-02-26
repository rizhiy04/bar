package com.example.Bar.service;

import com.example.Bar.converter.EventConverter;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.event.AddNewEventRequestDTO;
import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.entity.EventEntity;
import com.example.Bar.exception.BarNoSuchElementException;
import com.example.Bar.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventConverter eventConverter;

    @Cacheable("events")
    public List<EventDTO> getEvents(){
        return eventRepository.findAllByTimeAfterOrderByIdAsc(LocalDateTime.now()).stream()
                .map(eventConverter::convertToDTO).collect(Collectors.toList());
    }

    @CacheEvict(value = "events", allEntries = true)
    public void addEvent(final AddNewEventRequestDTO eventDTO){
        eventRepository.save(eventConverter.convertToEntity(eventDTO));
    }

    @CacheEvict(value = "events", allEntries = true)
    public void deleteEvent(final Integer eventId) throws BarNoSuchElementException {
        final EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new BarNoSuchElementException("Such eventEntity doesn't exist"));

        eventRepository.delete(eventEntity);
    }

}
