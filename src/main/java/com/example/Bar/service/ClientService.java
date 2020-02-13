package com.example.Bar.service;

import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.EventRepository;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.MenuItemRepository;
import com.example.Bar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public TextResponse reserveTable(final ReservationRequestDTO reserve) throws NoSuchElementException{
        ReservationEntity reservation = new ReservationEntity();
        InventoryEntity tables = inventoryRepository.findByCategory("table").orElseThrow(() -> new NoSuchElementException("Tables don't exist"));
        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByTimeAfterAndTimeBefore(reserve.getTime().minusHours(3), reserve.getTime().plusHours(3));

        if (reservationEntityList.size() == tables.getCount())
            return new TextResponse("Свободных мест на это время нет");

        List<Integer> reservedTable = new ArrayList<>();
        reservationEntityList.forEach(reservationEntity -> reservedTable.add(reservationEntity.getTableNumber()));

        for (int i = 1; i<= tables.getCount(); i++){
            if (!reservedTable.contains(i)){
                reservation.setTableNumber(i);
                break;
            }
        }

        reservation.setName(reserve.getName());
        reservation.setTime(reserve.getTime());
        reservationRepository.save(reservation);

        return new TextResponse("Ваш столик №" + reservation.getTableNumber());
    }

    public List<EventDTO> getEvents(){
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAllByTimeAfterOrderByIdAsc(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute())).stream().map(
                event -> new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getTime()))
                .collect(Collectors.toList());
    }

}
