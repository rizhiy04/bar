package com.example.Bar.service;

import com.example.Bar.dto.EventDTO;
import com.example.Bar.dto.MenuItemDTO;
import com.example.Bar.dto.ReservationRequestDTO;
import com.example.Bar.dto.TextResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    public List<MenuItemDTO> getMenu(){
        return Arrays.asList(new MenuItemDTO(1, "Zatecky Gus", "beer", "Светлый лагер с легким традиционным вкусом", 5d));
    }

    public List<MenuItemDTO> getMenuByCategory(String category){
        return Arrays.asList(new MenuItemDTO(12, "Пепперони", "pizza", "Колбаска пепперони, сыр. Пицца 30см", 15d));
    }

    public TextResponse reserveTable(ReservationRequestDTO reserve){
        return new TextResponse("Ваш столик №2");
    }

    public List<EventDTO> getEvents(){
        return Arrays.asList(new EventDTO(1, "StandUp вечер", "Много известных комиков", "04.03.2020"));
    }

}
