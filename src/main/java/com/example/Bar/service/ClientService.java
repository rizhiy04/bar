package com.example.Bar.service;

import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.entity.Event;
import com.example.Bar.entity.MenuItem;
import com.example.Bar.entity.Reservation;
import com.example.Bar.repository.EventRepository;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.MenuItemRepository;
import com.example.Bar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private final MenuItemRepository menuItemRepository;
    private final ReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final EventRepository eventRepository;

    public List<MenuItemDTO> getMenu(){
        List<MenuItemDTO> response = new ArrayList<>();

        for (MenuItem menuItem : menuItemRepository.findAll()){
            MenuItemDTO menuItemDTO = new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getCategory(), menuItem.getDescription(), menuItem.getPrice());
            response.add(menuItemDTO);
        }

        return response;
    }

    public List<MenuItemDTO> getMenuByCategory(final String category){
        List<MenuItemDTO> response = new ArrayList<>();

        for (MenuItem menuItem : menuItemRepository.findAllByCategory(category)){
            MenuItemDTO menuItemDTO = new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getCategory(), menuItem.getDescription(), menuItem.getPrice());
            response.add(menuItemDTO);
        }

        return response;
    }

    //TODO I need to think a little bit
    public TextResponse reserveTable(final ReservationRequestDTO reserve){
        Integer tableCount = inventoryRepository.findAllByCategory("table").get(0).getCount();
        List<Reservation> reservationList = reservationRepository.findAllByTimeAfterAndTimeBefore(reserve.getTime(), reserve.getTime().plusHours(3));

        if (reservationList.size() == tableCount)
            return new TextResponse("Свободных мест на это время нет");



        Reservation reservation = new Reservation();

        return null;
    }

    public List<EventDTO> getEvents(){
        List<EventDTO> response = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Event event : eventRepository.findAllByTimeAfterOrderByIdAsc(now)){
            EventDTO eventDTO = new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getTime());
            response.add(eventDTO);
        }

        return response;
    }

}
