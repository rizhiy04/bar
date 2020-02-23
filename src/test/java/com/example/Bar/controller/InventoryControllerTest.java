package com.example.Bar.controller;

import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InventoryControllerTest extends AbstractControllerTest{

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void testGetInventoryIsOk() throws Exception{
        final String token = signIn(Roles.ADMIN);

        inventoryRepository.deleteAll();
        final List<InventoryEntity> testInventoryEntities = getInventoryEntities();
        inventoryRepository.saveAll(testInventoryEntities);

        mockMvc.perform(get("/inventories").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "{\n" +
                        "  \"id\" : "+ testInventoryEntities.get(0).getId() +",\n" +
                        "  \"name\" : \"Рюмка 50 мл\",\n" +
                        "  \"category\" : \"glass\",\n" +
                        "  \"count\" : 23\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : "+ testInventoryEntities.get(1).getId() +",\n" +
                        "  \"name\" : \"Стол\",\n" +
                        "  \"category\" : \"table\",\n" +
                        "  \"count\" : 3\n" +
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

        inventoryRepository.deleteAll();
        final List<InventoryEntity> testInventoryEntities = getInventoryEntities();
        inventoryRepository.saveAll(testInventoryEntities);

        mockMvc.perform(patch("/inventories").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\" : "+ testInventoryEntities.get(0).getId() +",\n" +
                        "  \"count\" : 5\n" +
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
                        "  \"id\" : 2,\n" +
                        "  \"count\" : 5\n" +
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

        final List<InventoryEntity> testInventoryEntities = inventoryRepository.findAll();
        inventoryRepository.delete(testInventoryEntities.get(testInventoryEntities.size() - 1));
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

        inventoryRepository.deleteAll();
        final List<InventoryEntity> testInventoryEntities = getInventoryEntities();
        inventoryRepository.saveAll(testInventoryEntities);

        mockMvc.perform(delete("/inventories/" + testInventoryEntities.get(0).getId()).header("Authorization", token))
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

    private List<InventoryEntity> getInventoryEntities(){
        final InventoryEntity glass = new InventoryEntity();
        glass.setName("Рюмка 50 мл");
        glass.setCategory("glass");
        glass.setAmount(23);

        final InventoryEntity table = new InventoryEntity();
        table.setName("Стол");
        table.setCategory("table");
        table.setAmount(3);

        return Arrays.asList(glass, table);
    }

}