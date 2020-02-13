package com.example.Bar.controller;

import com.example.Bar.entity.EventEntity;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.entity.ReservationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientControllerTest extends AbstractControllerTest {

    @Test
    public void testGetMenuIsOk() throws Exception{

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(1);
        menuItemEntity.setName("Zatecky Gus");
        menuItemEntity.setCategory("beer");
        menuItemEntity.setDescription("Светлый лагер с легким традиционным вкусом");
        menuItemEntity.setPrice(5d);

        given(menuItemRepository.findAll()).willReturn(Collections.singletonList(menuItemEntity));

        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testGetMenuByCategoryIsOk() throws Exception{

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(12);
        menuItemEntity.setName("Пепперони");
        menuItemEntity.setCategory("pizza");
        menuItemEntity.setDescription("Колбаска пепперони, сыр. Пицца 30см");
        menuItemEntity.setPrice(15d);

        given(menuItemRepository.findAllByCategory("pizza")).willReturn(Collections.singletonList(menuItemEntity));

        mockMvc.perform(get("/menu/pizza"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 12,\n" +
                        "  \"name\" : \"Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Колбаска пепперони, сыр. Пицца 30см\",\n" +
                        "  \"price\" : 15\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testReserveTableIsCreated() throws Exception{

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(100);
        inventoryEntity.setName("Стол");
        inventoryEntity.setCategory("table");
        inventoryEntity.setCount(5);

        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(inventoryEntity));

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1000);
        reservationEntity.setName("Qwer");
        reservationEntity.setTime(LocalDateTime.of(2020, 3,4,17,0));
        reservationEntity.setTableNumber(1);

        LocalDateTime reserveTime = LocalDateTime.of(2020, 3,4,19,0);
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(reserveTime.minusHours(3), reserveTime.plusHours(3))).willReturn(Collections.singletonList(reservationEntity));

        mockMvc.perform(post("/reserveTable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\" : \"Денис\",\n" +
                                "  \"time\" : \"04-03-2020 19:00\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Ваш столик №2\"\n" +
                        "}"));
    }

    @Test
    public void testGetEventsIsOk() throws Exception{

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1);
        eventEntity.setName("StandUp вечер");
        eventEntity.setDescription("Много известных комиков");
        eventEntity.setTime(LocalDateTime.of(2020, 3,4,20,0));

        given(eventRepository.findAllByTimeAfterOrderByIdAsc(any(LocalDateTime.class))).willReturn(Collections.singletonList(eventEntity));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"04-03-2020 20:00\"\n" +
                        "}\n" +
                        "]"));
    }

}