package com.example.Bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMenuIsOk() throws Exception{
        mockMvc.perform(get("/bar/menu"))
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
        mockMvc.perform(get("/bar/menu/pizza"))
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
        mockMvc.perform(post("/bar/reserveTable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\" : \"Денис\",\n" +
                                "  \"time\" : \"04.03.2020 19:00\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Ваш столик №2\"\n" +
                        "}"));
    }

    @Test
    public void testGetEventsIsOk() throws Exception{
        mockMvc.perform(get("/bar/events"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"eventName\" : \"StandUp вечер\",\n" +
                        "  \"description\" : \"Много известных комиков\",\n" +
                        "  \"date\" : \"04.03.2020\"\n" +
                        "}\n" +
                        "]"));
    }

}