package com.example.Bar.controller;

import com.example.Bar.entity.EventEntity;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTest extends AbstractControllerTest {

    @Test
    public void testGetInventoryIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Рюмка 50 мл");
        inventoryEntity.setCategory("glass");
        inventoryEntity.setCount(23);

        given(inventoryRepository.findAll()).willReturn(Collections.singletonList(inventoryEntity));

        mockMvc.perform(get("/admin/inventoryCount").header("Authorization", token))
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
    public void testGetInventoryAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(get("/admin/inventoryCount").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetInventoryAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/admin/inventoryCount").header("Authorization", token))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testChangeInventoryCountIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Рюмка 50 мл");
        inventoryEntity.setCategory("glass");
        inventoryEntity.setCount(23);

        given(inventoryRepository.findById(1)).willReturn(Optional.of(inventoryEntity));

        mockMvc.perform(post("/admin/inventoryCount").header("Authorization", token)
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
    public void testChangeInventoryAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(post("/admin/inventoryCount").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"count\" : 50\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testChangeInventoryAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(post("/admin/inventoryCount").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"count\" : 50\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testAddNewInventoryIsCreated() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(post("/admin/newInventory").header("Authorization", token)
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
    public void testAddNewInventoryAccessDeniedForWaiter() throws Exception {
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(post("/admin/newInventory").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Бокал 500 мл\",\n" +
                        "  \"category\" : \"wineglass\",\n" +
                        "  \"count\" : 30\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAddNewInventoryAccessDeniedForClient() throws Exception {
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(post("/admin/newInventory").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Бокал 500 мл\",\n" +
                        "  \"category\" : \"wineglass\",\n" +
                        "  \"count\" : 30\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testDeleteInventoryIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Рюмка 50 мл");
        inventoryEntity.setCategory("glass");
        inventoryEntity.setCount(23);

        given(inventoryRepository.findById(1)).willReturn(Optional.of(inventoryEntity));

        mockMvc.perform(delete("/admin/deleteInventory/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Инвентарь удален\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteInventoryAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(delete("/admin/deleteInventory/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteInventoryAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(delete("/admin/deleteInventory/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testAddNewEventIsCreated() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(post("/admin/addNewEvent").header("Authorization", token)
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

        mockMvc.perform(post("/admin/addNewEvent").header("Authorization", token)
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

        mockMvc.perform(post("/admin/addNewEvent").header("Authorization", token)
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

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1);
        eventEntity.setName("StandUp вечер");
        eventEntity.setDescription("Много известных комиков");
        eventEntity.setTime(LocalDateTime.of(2020, 3, 4, 20, 0));

        given(eventRepository.findById(1)).willReturn(Optional.of(eventEntity));

        mockMvc.perform(delete("/admin/deleteEvent/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Мероприятие удалено\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteEventAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(delete("/admin/deleteEvent/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteEventAccessDeniedForClient() throws Exception {
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(delete("/admin/deleteEvent/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testAddNewMenuItemIsCreated() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(post("/admin/addNewMenuItem").header("Authorization", token)
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
    public void testAddNewMenuItemAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(post("/admin/addNewMenuItem").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAddNewMenuItemAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(post("/admin/addNewMenuItem").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testDeleteMenuItemIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(1);
        menuItemEntity.setName("Zatecky Gus");
        menuItemEntity.setCategory("beer");
        menuItemEntity.setDescription("Светлый лагер с легким традиционным вкусом");
        menuItemEntity.setPrice(5d);

        given(menuItemRepository.findById(1)).willReturn(Optional.of(menuItemEntity));

        mockMvc.perform(delete("/admin/deleteMenuItem/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Позиция удалена\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteMenuItemAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(delete("/admin/deleteMenuItem/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteMenuItemAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(delete("/admin/deleteMenuItem/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }
}