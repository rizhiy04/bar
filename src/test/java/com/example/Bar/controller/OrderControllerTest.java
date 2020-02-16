package com.example.Bar.controller;

import com.example.Bar.repository.OrderRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends AbstractControllerTest{

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testNewOrderIsCreated() throws Exception{
        final String token = signIn(Roles.WAITER);

        mockMvc.perform(post("/order").header("Authorization", token)
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

        orderRepository.delete(orderRepository.findAll().get(1));
    }

    @Test
    public void testNewOrderAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(post("/order").header("Authorization", token)
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

        mockMvc.perform(patch("/order").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Заказ закрыт, к оплате 25.0р\"\n" +
                        "}"));
    }

    @Test
    public void testCloseOrderAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(patch("/order").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

}