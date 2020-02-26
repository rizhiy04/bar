package com.example.Bar.controller;

import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

class MenuControllerTest extends AbstractControllerTest{

    @Test
    public void testGetMenuIsOk() throws Exception{
        //given
        final List<MenuItemEntity> testMenuItemEntities = getMenuItems();
        given(menuItemRepository.findAll()).willReturn(testMenuItemEntities);

        //when
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 12,\n" +
                        "  \"name\" : \"Пепперони\",\n" +
                        "  \"category\" : \"pizza\",\n" +
                        "  \"description\" : \"Колбаска пепперони, сыр. Пицца 30см\",\n" +
                        "  \"price\" : 15\n" +
                        "}\n" +
                        "]"));

        verify(menuItemRepository, times(1)).findAll();
    }

    @Test
    public void testGetMenuByCategoryIsOk() throws Exception{
        //given
        final List<MenuItemEntity> testMenuItemEntities = getMenuItems();
        given(menuItemRepository.findAllByCategory("pizza"))
                .willReturn(Collections.singletonList(testMenuItemEntities.get(1)));

        //when
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

        verify(menuItemRepository, times(1)).findAllByCategory("pizza");
    }

    @Test
    public void testAddNewMenuItemIsCreated() throws Exception{
        //given
        final String token = signIn(Roles.ADMIN);

        //when
        mockMvc.perform(post("/menu").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Бургер\",\n" +
                        "  \"category\" : \"burger\",\n" +
                        "  \"description\" : \"Большой и вкусный\",\n" +
                        "  \"price\" : 5\n" +
                        "}"))
                .andExpect(status().isCreated());

        verify(menuItemRepository, times(1)).save(any(MenuItemEntity.class));
    }

    @Test
    public void testAddNewMenuItemAccessDeniedForWaiter() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);

        //when
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
        //given
        final String token = signIn(Roles.CLIENT);

        //when
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
        //given
        final String token = signIn(Roles.ADMIN);
        final List<MenuItemEntity> testMenuItemEntities = getMenuItems();
        given(menuItemRepository.findById(1)).willReturn(Optional.of(testMenuItemEntities.get(0)));

        //when
        mockMvc.perform(delete("/menu/1").header("Authorization", token))
                .andExpect(status().isOk());

        verify(menuItemRepository, times(1)).findById(1);
        verify(menuItemRepository, times(1)).delete(any(MenuItemEntity.class));
    }

    @Test
    public void testDeleteMenuItemAccessDeniedForWaiter() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);

        //when
        mockMvc.perform(delete("/menu/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteMenuItemAccessDeniedForClient() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(delete("/menu/1").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testDeleteMenuItemBarNoSuchElementException() throws Exception{
        //given
        final String token = signIn(Roles.ADMIN);
        given(menuItemRepository.findById(1)).willReturn(Optional.empty());

        //when
        mockMvc.perform(delete("/menu/1").header("Authorization", token))
                .andExpect(status().isBadRequest());

        verify(menuItemRepository, times(1)).findById(1);
        verify(menuItemRepository, times(0)).delete(any(MenuItemEntity.class));

    }

    private List<MenuItemEntity> getMenuItems(){
        final MenuItemEntity beer = new MenuItemEntity();
        beer.setId(1);
        beer.setName("Zatecky Gus");
        beer.setCategory("beer");
        beer.setDescription("Светлый лагер с легким традиционным вкусом");
        beer.setPrice(BigDecimal.valueOf(5));

        final MenuItemEntity pizza = new MenuItemEntity();
        pizza.setId(12);
        pizza.setName("Пепперони");
        pizza.setCategory("pizza");
        pizza.setDescription("Колбаска пепперони, сыр. Пицца 30см");
        pizza.setPrice(BigDecimal.valueOf(15));

        return Arrays.asList(beer, pizza);
    }

}