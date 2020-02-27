package com.example.Bar.controller;

import com.example.Bar.entity.EventEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerTest extends AbstractControllerTest {

    @Test
    public void testGetEventsIsOk() throws Exception{
        //given
        final EventEntity testEventEntity = getEventEntity();
        given(eventRepository.findAllByTimeAfterOrderByIdAsc(any(LocalDateTime.class)))
                .willReturn(Collections.singletonList(testEventEntity));

        //when
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

        verify(eventRepository, times(1)).findAllByTimeAfterOrderByIdAsc(any(LocalDateTime.class));
    }

    @Test
    public void testAddNewEventIsCreated() throws Exception{
        //given
        final String token = signIn(Roles.ADMIN);

        //when
        mockMvc.perform(post("/events").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"14-03-2020 20:00\"\n" +
                        "}"))
                .andExpect(status().isCreated());

        verify(eventRepository,times(1)).save(any(EventEntity.class));
    }

    @Test
    public void testAddNewEventAccessDeniedForWaiter() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);

        //when
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
        //given
        final String token = signIn(Roles.CLIENT);

        //when
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
        //given
        final String token = signIn(Roles.ADMIN);
        final EventEntity testEventEntity = getEventEntity();
        given(eventRepository.findById(1)).willReturn(Optional.of(testEventEntity));

        //when
        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isOk());

        verify(eventRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).delete(any(EventEntity.class));
    }

    @Test
    public void testDeleteEventAccessDeniedForWaiter() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);

        //when
        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteEventAccessDeniedForClient() throws Exception {
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteEventThrowsBarNoSuchElementException() throws Exception{
        //given
        final String token = signIn(Roles.ADMIN);
        given(eventRepository.findById(1)).willReturn(Optional.empty());

        //when
        mockMvc.perform(delete("/events/1").header("Authorization", token))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\n" +
                        "  \"errorMessage\" : \"Such eventEntity doesn't exist\"\n" +
                        "}"));

        verify(eventRepository, times(1)).findById(1);
        verify(eventRepository, times(0)).delete(any(EventEntity.class));
    }

    private EventEntity getEventEntity(){
        final EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1);
        eventEntity.setName("StandUp вечер");
        eventEntity.setDescription("Много известных комиков");
        eventEntity.setTime(LocalDateTime.of(2020,3,4, 20,0));

        return eventEntity;
    }

}