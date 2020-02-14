package com.example.Bar.controller;

import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InventoryControllerTest extends AbstractControllerTest{

    @Test
    public void testGetInventoryIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Рюмка 50 мл");
        inventoryEntity.setCategory("glass");
        inventoryEntity.setCount(23);

        given(inventoryRepository.findAll()).willReturn(Collections.singletonList(inventoryEntity));

        mockMvc.perform(get("/inventories").header("Authorization", token))
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

        mockMvc.perform(get("/inventories").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetInventoryAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/inventories").header("Authorization", token))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testChangeInventoryCountIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Рюмка 50 мл");
        inventoryEntity.setCategory("glass");
        inventoryEntity.setCount(23);

        given(inventoryRepository.findById(1)).willReturn(Optional.of(inventoryEntity));

        mockMvc.perform(patch("/inventories").header("Authorization", token)
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

        mockMvc.perform(patch("/inventories").header("Authorization", token)
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

        mockMvc.perform(patch("/inventories").header("Authorization", token)
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

        mockMvc.perform(post("/inventories").header("Authorization", token)
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

        mockMvc.perform(post("/inventories").header("Authorization", token)
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

        mockMvc.perform(post("/inventories").header("Authorization", token)
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

        final InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setName("Рюмка 50 мл");
        inventoryEntity.setCategory("glass");
        inventoryEntity.setCount(23);

        given(inventoryRepository.findById(1)).willReturn(Optional.of(inventoryEntity));

        mockMvc.perform(delete("/inventories/1").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Инвентарь удален\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteInventoryAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(delete("/inventories/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteInventoryAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(delete("/inventories/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

}