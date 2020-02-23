package com.example.Bar.controller;

import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.repository.MenuItemRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends AbstractControllerTest{

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    public void testGetMenuIsOk() throws Exception{
        menuItemRepository.deleteAll();
        final List<MenuItemEntity> testMenuItemEntities = getMenuItems();
        menuItemRepository.saveAll(testMenuItemEntities);

        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : "+ testMenuItemEntities.get(0).getId() +",\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : "+ testMenuItemEntities.get(1).getId() +",\n" +
                        "  \"name\" : \"Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Колбаска пепперони, сыр. Пицца 30см\",\n" +
                        "  \"price\" : 15\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testGetMenuByCategoryIsOk() throws Exception{
        menuItemRepository.deleteAll();
        final List<MenuItemEntity> testMenuItemEntities = getMenuItems();
        menuItemRepository.saveAll(testMenuItemEntities);

        mockMvc.perform(get("/menu/pizza"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : "+ testMenuItemEntities.get(1).getId() +",\n" +
                        "  \"name\" : \"Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Колбаска пепперони, сыр. Пицца 30см\",\n" +
                        "  \"price\" : 15\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testAddNewMenuItemIsCreated() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(post("/menu").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Бургер\",\n" +
                        "  \"category\" : \"burger\",\n" +
                        "  \"description\" : \"Большой и вкусный\",\n" +
                        "  \"price\" : 5\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Позиция добавлена\"\n" +
                        "}"));

        final List<MenuItemEntity> testMenuItems = menuItemRepository.findAll();
        menuItemRepository.delete(testMenuItems.get(testMenuItems.size() - 1));
    }

    @Test
    public void testAddNewMenuItemAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(post("/menu").header("Authorization", token)
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

        mockMvc.perform(post("/menu").header("Authorization", token)
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

        menuItemRepository.deleteAll();
        final List<MenuItemEntity> testMenuItemEntities = getMenuItems();
        menuItemRepository.saveAll(testMenuItemEntities);

        mockMvc.perform(delete("/menu/" + testMenuItemEntities.get(0).getId()).header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Позиция удалена\"\n" +
                        "}"));
    }

    @Test
    public void testDeleteMenuItemAccessDeniedForWaiter() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(delete("/menu/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteMenuItemAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(delete("/menu/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    private List<MenuItemEntity> getMenuItems(){
        final MenuItemEntity beer = new MenuItemEntity();
        beer.setName("Zatecky Gus");
        beer.setCategory("beer");
        beer.setDescription("Светлый лагер с легким традиционным вкусом");
        beer.setPrice(5D);

        final MenuItemEntity pizza = new MenuItemEntity();
        pizza.setName("Пепперони");
        pizza.setCategory("pizza");
        pizza.setDescription("Колбаска пепперони, сыр. Пицца 30см");
        pizza.setPrice(15D);

        return Arrays.asList(beer, pizza);
    }

}