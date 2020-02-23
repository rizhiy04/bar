package com.example.Bar.controller;

import com.example.Bar.entity.EventEntity;
import com.example.Bar.repository.EventRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerTest extends AbstractControllerTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testGetEventsIsOk() throws Exception{
        eventRepository.deleteAll();
        final EventEntity testEventEntity = getEventEntity();
        eventRepository.save(testEventEntity);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : "+ testEventEntity.getId() +",\n" +
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

        final List<EventEntity> testEvents = eventRepository.findAll();
        eventRepository.delete(testEvents.get(testEvents.size()-1));
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

        final EventEntity testEventEntity = getEventEntity();
        eventRepository.save(testEventEntity);

        mockMvc.perform(delete("/events/" + testEventEntity.getId()).header("Authorization", token))
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

    private EventEntity getEventEntity(){
        final EventEntity eventEntity = new EventEntity();
        eventEntity.setName("StandUp вечер");
        eventEntity.setDescription("Много известных комиков");
        eventEntity.setTime(LocalDateTime.of(2020,3,4, 20,0));

        return eventEntity;
    }

}