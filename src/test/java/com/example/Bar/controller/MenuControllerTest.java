package com.example.Bar.controller;

import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MenuControllerTest extends AbstractControllerTest{

    @Test
    public void testGetMenuIsOk() throws Exception{

        final MenuItemEntity menuItemEntity = new MenuItemEntity();
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

        final MenuItemEntity menuItemEntity = new MenuItemEntity();
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
    public void testAddNewMenuItemIsCreated() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(post("/menu").header("Authorization", token)
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

        final MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(1);
        menuItemEntity.setName("Zatecky Gus");
        menuItemEntity.setCategory("beer");
        menuItemEntity.setDescription("Светлый лагер с легким традиционным вкусом");
        menuItemEntity.setPrice(5d);

        given(menuItemRepository.findById(1)).willReturn(Optional.of(menuItemEntity));

        mockMvc.perform(delete("/menu/1").header("Authorization", token))
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

}