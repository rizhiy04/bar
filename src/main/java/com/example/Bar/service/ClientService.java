package com.example.Bar.service;

import com.example.Bar.dto.event.EventDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ClientService {

    public List<MenuItemDTO> getMenu(){
        return Collections.singletonList(new MenuItemDTO(1, "Zatecky Gus", "beer", "Светлый лагер с легким традиционным вкусом", 5d));
    }

    public List<MenuItemDTO> getMenuByCategory(final String category){
        return Collections.singletonList(new MenuItemDTO(12, "Пепперони", "pizza", "Колбаска пепперони, сыр. Пицца 30см", 15d));
    }

    public TextResponse reserveTable(final ReservationRequestDTO reserve){
        System.out.println(reserve.getTime());
        return new TextResponse("Ваш столик №2");
    }

    public List<EventDTO> getEvents(){
        return Collections.singletonList(new EventDTO(1, "StandUp вечер", "Много известных комиков", LocalDateTime.of(2020, 3, 4, 20, 0)));
    }

}
