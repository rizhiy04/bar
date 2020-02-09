package com.example.Bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetInventoryIsOk() throws Exception{
        mockMvc.perform(get("/admin/inventoryCount"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Рюмка 50 мл\",\n" +
                        "  \"category\" : \"glass\",\n" +
                        "  \"count\" : 23\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testChangeInventoryCountIsOk() throws Exception{
        mockMvc.perform(post("/admin/inventoryCount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\" : 1,\n" +
                                "  \"count\" : 50\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Сохранено\"\n" +
                        "}"));
    }

    @Test
    public void testAddNewInventoryIsCreated() throws Exception{
        mockMvc.perform(post("/admin/newInventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\" : \"Бокал 500 мл\",\n" +
                                "  \"category\" : \"wineglass\",\n" +
                                "  \"count\" : 30\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Инвентарь добавлен\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteInventoryIsOk() throws Exception{
        mockMvc.perform(delete("/admin/deleteInventory/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Инвентарь удален\"\n" +
                        "}"));
    }

    @Test
    public void testAddNewEventIsCreated() throws Exception{
        mockMvc.perform(post("/admin/addNewEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"eventName\" : \"StandUp вечер\",\n" +
                                "  \"description\" : \"Много известных комиков\",\n" +
                                "  \"date\" : \"14.03.2020\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Мероприятие добавлено\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteEventIsOk() throws Exception{
        mockMvc.perform(delete("/admin/deleteEvent/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Мероприятие удалено\"\n" +
                        "}"));
    }

    @Test
    public void testAddNewMenuItemIsCreated() throws Exception{
        mockMvc.perform(post("/admin/addNewMenuItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\" : \"Zatecky Gus\",\n" +
                                "  \"category\" : \"beer\",\n" +
                                "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                                "  \"price\" : 5\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Позиция добавлена\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteMenuItemIsOk() throws Exception{
        mockMvc.perform(delete("/admin/deleteMenuItem/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Позиция удалена\"\n" +
                        "}"));
    }

}