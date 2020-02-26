package com.example.Bar.controller;

import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.entity.OrderChoiceEntity;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends AbstractControllerTest{

    @Test
    public void testGetOrders() throws Exception{
        //given
        final String token = signIn(Roles.ADMIN);
        final MenuItemEntity testMenuItemEntity = getMenuItemEntity();
        final List<OrderEntity> testOrderEntities = getOrderEntities();
        given(menuItemRepository.findById(1)).willReturn(Optional.of(testMenuItemEntity));
        given(orderRepository.findAll()).willReturn(testOrderEntities);

        //when
        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"timeOpen\" : \"14-03-2020 20:00\",\n" +
                        "  \"timeClose\" : null,\n" +
                        "  \"order\" : \n" +
                        "[\n"+
                        "{\n" +
                        "  \"menuItem\" : \n" +
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "},\n" +
                        "{\n" +
                        "  \"id\" : 2,\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"timeOpen\" : \"14-01-2020 20:00\",\n" +
                        "  \"timeClose\" : \"14-01-2020 22:00\",\n" +
                        "  \"order\" : \n" +
                        "[\n"+
                        "{\n" +
                        "  \"menuItem\" : \n" +
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"name\" : \"Zatecky Gus\",\n" +
                        "  \"category\" : \"beer\",\n" +
                        "  \"description\" : \"Светлый лагер с легким традиционным вкусом\",\n" +
                        "  \"price\" : 5\n" +
                        "},\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testGetOrdersAccessDeniedForWaiter() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);

        //when
        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetOrdersAccessDeniedForClient() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetRevenueByDateIsOk() throws Exception{
        //given
        final String token = signIn(Roles.ADMIN);
        final List<OrderEntity> testOrderEntities = getOrderEntities();
        given(orderRepository.findAllByTimeCloseAfter(LocalDateTime.of(2019, 3,14,0,0)))
                .willReturn(Collections.singletonList(testOrderEntities.get(1)));

        //when
        mockMvc.perform(get("/orders/revenue").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"date\" : \"14-03-2019 00:00\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"25.0р.\"\n" +
                        "}"));
    }

    @Test
    public void testGetRevenueByDateAccessDeniedForWaiter() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);

        //when
        mockMvc.perform(get("/orders/revenue").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"date\" : \"14-03-2019 00:00\"\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetRevenueByDateAccessDeniedForClient() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(get("/orders/revenue").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"date\" : \"14-03-2019 00:00\"\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testNewOrderIsCreated() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);
        final MenuItemEntity testMenuItem = getMenuItemEntity();
        given(menuItemRepository.findById(1)).willReturn(Optional.of(testMenuItem));

        //when
        mockMvc.perform(post("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"order\" : [\n" +
                        "{\n"+
                        "  \"id\" : 1,\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "}"))
                .andExpect(status().isCreated());

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testNewOrderAccessDeniedForClient() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);

        //when
        mockMvc.perform(post("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"orderEntity\" : [\n" +
                        "{\n"+
                        "  \"id\" : 1,\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCloseOrderIsOk() throws Exception{
        //given
        final String token = signIn(Roles.WAITER);
        final List<OrderEntity> testOrderEntities = getOrderEntities();
        given(orderRepository.findByTableNumberAndTimeCloseIsNull(2))
                .willReturn(Optional.of(testOrderEntities.get(0)));

        //when
        mockMvc.perform(patch("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"25.0р\"\n" +
                        "}"));

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testCloseOrderAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(patch("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    private List<OrderEntity> getOrderEntities(){
        final List<OrderChoiceEntity> firstOrderChoiceEntities = Collections.singletonList(getOrderChoiceEntity());
        final OrderEntity firstOrderEntity = new OrderEntity();
        firstOrderEntity.setId(1);
        firstOrderEntity.setTableNumber(2);
        firstOrderEntity.setTimeOpen(LocalDateTime.of(2020,3,14,20,0));
        firstOrderEntity.setOrderChoiceEntities(firstOrderChoiceEntities);
        firstOrderChoiceEntities.forEach(orderChoiceEntity -> orderChoiceEntity.setOrderEntity(firstOrderEntity));

        final List<OrderChoiceEntity> secondOrderChoiceEntities = Collections.singletonList(getOrderChoiceEntity());
        final OrderEntity secondOrderEntity = new OrderEntity();
        secondOrderEntity.setId(2);
        secondOrderEntity.setTableNumber(2);
        secondOrderEntity.setTimeOpen(LocalDateTime.of(2020,1,14,20,0));
        secondOrderEntity.setTimeClose(LocalDateTime.of(2020,1,14,22,0));
        secondOrderEntity.setOrderChoiceEntities(secondOrderChoiceEntities);
        secondOrderChoiceEntities.forEach(orderChoiceEntity -> orderChoiceEntity.setOrderEntity(secondOrderEntity));

        return Arrays.asList(firstOrderEntity, secondOrderEntity);
    }

    private OrderChoiceEntity getOrderChoiceEntity(){
        final OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
        orderChoiceEntity.setMenuItemEntity(getMenuItemEntity());
        orderChoiceEntity.setAmount(5);

        return orderChoiceEntity;
    }

    private MenuItemEntity getMenuItemEntity(){
        final MenuItemEntity beer = new MenuItemEntity();
        beer.setId(1);
        beer.setName("Zatecky Gus");
        beer.setCategory("beer");
        beer.setDescription("Светлый лагер с легким традиционным вкусом");
        beer.setPrice(5D);

        menuItemRepository.save(beer);

        return beer;
    }

}