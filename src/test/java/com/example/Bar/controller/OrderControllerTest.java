package com.example.Bar.controller;

import com.example.Bar.entity.OrderEntity;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends AbstractControllerTest{

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testGetOrders() throws Exception{
        final String token = signIn(Roles.ADMIN);

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
                        "  \"id\" : 3,\n" +
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
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetOrdersAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetRevenueByDate() throws Exception{
        final String token = signIn(Roles.ADMIN);

        mockMvc.perform(get("/orders/revenue").header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"date\" : \"14-03-2019 00:00\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Выручка: 25.0р.\"\n" +
                        "}"));
    }


    @Test
    public void testNewOrderIsCreated() throws Exception{
        final String token = signIn(Roles.WAITER);

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
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Заказ оформлен\"\n" +
                        "}"));

        final List<OrderEntity> orderEntities = orderRepository.findAll();
        orderRepository.delete(orderEntities.get(orderEntities.size()-1));
    }

    @Test
    public void testNewOrderAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

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
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(patch("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Заказ закрыт, к оплате 25.0р\"\n" +
                        "}"));

        final OrderEntity orderEntity = orderRepository.findAll().get(0);
        orderEntity.setTimeClose(null);
        orderRepository.save(orderEntity);
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

}