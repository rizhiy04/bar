package com.example.Bar.service;

import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.repository.EventRepository;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.MenuItemRepository;
import com.example.Bar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {

    private final MenuItemRepository menuItemRepository;
    private final ReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final EventRepository eventRepository;

    public List<MenuItemDTO> getMenu(){
        return menuItemRepository.findAll().stream().map(
                menuItem -> new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getCategory(), menuItem.getDescription(), menuItem.getPrice()))
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> getMenuByCategory(final String category){
        return menuItemRepository.findAllByCategory(category).stream().map(
                menuItem -> new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getCategory(), menuItem.getDescription(), menuItem.getPrice()))
                .collect(Collectors.toList());
    }

    //TODO I need to think a little bit
    public TextResponse reserveTable(final ReservationRequestDTO reserve){
        Integer tableCount = inventoryRepository.findAllByCategory("table").get(0).getCount();
        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByTimeAfterAndTimeBefore(reserve.getTime(), reserve.getTime().plusHours(3));

        if (reservationEntityList.size() == tableCount)
            return new TextResponse("Свободных мест на это время нет");



        ReservationEntity reservationEntity = new ReservationEntity();

        return null;
    }

    public List<EventDTO> getEvents(){
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAllByTimeAfterOrderByIdAsc(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute())).stream().map(
                event -> new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getTime()))
                .collect(Collectors.toList());
    }

}
