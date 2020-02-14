package com.example.Bar.controller;

import com.example.Bar.entity.EventEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EventControllerTest extends AbstractControllerTest {

    @Test
    public void testGetEventsIsOk() throws Exception{

        final EventEntity eventEntity = new EventEntity();
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

    @Test
    public void testAddNewEventIsCreated() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(post("/events").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"14-03-2020 20:00\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Мероприятие добавлено\"\n" +
                        "}"));
    }

    @Test
    public void testAddNewEventAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(post("/events").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"14-03-2020 20:00\"\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAddNewEventAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(post("/events").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"14-03-2020 20:00\"\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testDeleteEventIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        final EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1);
        eventEntity.setName("StandUp вечер");
        eventEntity.setDescription("Много известных комиков");
        eventEntity.setTime(LocalDateTime.of(2020, 3, 4, 20, 0));

        given(eventRepository.findById(1)).willReturn(Optional.of(eventEntity));

        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Мероприятие удалено\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteEventAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteEventAccessDeniedForClient() throws Exception {
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

}