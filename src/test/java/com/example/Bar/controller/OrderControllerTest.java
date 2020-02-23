package com.example.Bar.controller;

import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.entity.OrderChoiceEntity;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.repository.MenuItemRepository;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.repository.UserRepository;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends AbstractControllerTest{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    public void testGetOrders() throws Exception{
        final String token = signIn(Roles.ADMIN);

        orderRepository.deleteAll();
        menuItemRepository.deleteAll();
        final List<OrderEntity> testOrderEntities = getOrderEntities();
        orderRepository.saveAll(testOrderEntities);

        mockMvc.perform(get("/orders").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : "+ testOrderEntities.get(0).getId() +",\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"timeOpen\" : \"14-03-2020 20:00\",\n" +
                        "  \"timeClose\" : null,\n" +
                        "  \"order\" : \n" +
                        "[\n"+
                        "{\n" +
                        "  \"menuItem\" : \n" +
                        "{\n" +
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
                        "  \"id\" : "+ testOrderEntities.get(1).getId() +",\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"timeOpen\" : \"14-01-2020 20:00\",\n" +
                        "  \"timeClose\" : \"14-01-2020 22:00\",\n" +
                        "  \"order\" : \n" +
                        "[\n"+
                        "{\n" +
                        "  \"menuItem\" : \n" +
                        "{\n" +
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

        orderRepository.deleteAll();
        menuItemRepository.deleteAll();
        final List<OrderEntity> testOrderEntities = getOrderEntities();
        orderRepository.saveAll(testOrderEntities);

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

        orderRepository.deleteAll();
        menuItemRepository.deleteAll();
        final MenuItemEntity testMenuItem = getMenuItemEntity();

        mockMvc.perform(post("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"order\" : [\n" +
                        "{\n"+
                        "  \"id\" : "+ testMenuItem.getId() +",\n" +
                        "  \"count\" : 5\n" +
                        "}\n" +
                        "]\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Заказ оформлен\"\n" +
                        "}"));

        final List<OrderEntity> testOrderEntities = orderRepository.findAll();
        orderRepository.delete(testOrderEntities.get(testOrderEntities.size() - 1));
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

        orderRepository.deleteAll();
        menuItemRepository.deleteAll();
        final List<OrderEntity> testOrderEntities = getOrderEntities();
        orderRepository.saveAll(testOrderEntities);

        mockMvc.perform(patch("/orders").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : "+ testOrderEntities.get(0).getTableNumber() +",\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Заказ закрыт, к оплате 25.0р\"\n" +
                        "}"));

        final UserEntity userEntity = userRepository.findById(1).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        userEntity.getUserDiscountCardEntity().setAllSpentMoney(0D);
        userRepository.save(userEntity);
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
        firstOrderEntity.setTableNumber(2);
        firstOrderEntity.setTimeOpen(LocalDateTime.of(2020,3,14,20,0));
        firstOrderEntity.setOrderChoiceEntities(firstOrderChoiceEntities);
        firstOrderChoiceEntities.forEach(orderChoiceEntity -> orderChoiceEntity.setOrderEntity(firstOrderEntity));

        final List<OrderChoiceEntity> secondOrderChoiceEntities = Collections.singletonList(getOrderChoiceEntity());
        final OrderEntity secondOrderEntity = new OrderEntity();
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
        beer.setName("Zatecky Gus");
        beer.setCategory("beer");
        beer.setDescription("Светлый лагер с легким традиционным вкусом");
        beer.setPrice(5D);

        menuItemRepository.save(beer);

        return beer;
    }

}