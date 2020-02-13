package com.example.Bar.controller;

import com.example.Bar.entity.*;
import com.example.Bar.security.Roles;
import lombok.extern.java.Log;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WaiterControllerTest extends AbstractControllerTest {

    @Test
    public void testGetReservationIsOk() throws Exception{
        final String token = signIn(Roles.WAITER);

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1);
        reservationEntity.setName("Денис");
        reservationEntity.setTableNumber(2);
        reservationEntity.setTime(LocalDateTime.of(2020, 3,4,19,0));

        LocalDateTime now = LocalDateTime.now();
        given(reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute()))).willReturn(Collections.singletonList(reservationEntity));

        mockMvc.perform(get("/waiter/reservation").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n"+
                        "{\n" +
                        "  \"id\" : 1,\n" +
                        "  \"clientName\" : \"Денис\",\n" +
                        "  \"reserveTime\" : \"04-03-2020 19:00\",\n" +
                        "  \"tableNumber\" : 2\n" +
                        "}\n" +
                        "]"));
    }

    @Test
    public void testGetReservationAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/waiter/reservation").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetFreeTablesIsOk() throws Exception{
        final String token = signIn(Roles.WAITER);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(100);
        inventoryEntity.setName("Стол");
        inventoryEntity.setCategory("table");
        inventoryEntity.setCount(3);

        given(inventoryRepository.findByCategory("table")).willReturn(Optional.of(inventoryEntity));

        LocalDateTime now = LocalDateTime.now();
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1000);
        reservationEntity.setName("Qwer");
        reservationEntity.setTime(now.plusHours(1));
        reservationEntity.setTableNumber(2);

        //TODO doesn't work
        given(reservationRepository.findAllByTimeAfterAndTimeBefore(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute()), now.plusHours(2))).willReturn(Collections.singletonList(reservationEntity));

        mockMvc.perform(get("/waiter/freeTables/2").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tableNumbers\" : [1, 3]\n" +
                        "}\n"));
    }

    @Test
    public void testGetFreeTablesAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/waiter/freeTables/2").header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testNewOrderIsCreated() throws Exception{
        final String token = signIn(Roles.WAITER);

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(1);
        menuItemEntity.setName("Zatecky Gus");
        menuItemEntity.setCategory("beer");
        menuItemEntity.setDescription("Светлый лагер с легким традиционным вкусом");
        menuItemEntity.setPrice(5d);

        given(menuItemRepository.findById(1)).willReturn(Optional.of(menuItemEntity));

        mockMvc.perform(post("/waiter/makeOrder").header("Authorization", token)
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
    }

    @Test
    public void testNewOrderAccessDeniedForClient() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(post("/waiter/makeOrder").header("Authorization", token)
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

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setId(1);
        menuItemEntity.setName("Zatecky Gus");
        menuItemEntity.setCategory("beer");
        menuItemEntity.setDescription("Светлый лагер с легким традиционным вкусом");
        menuItemEntity.setPrice(5d);

        OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
        orderChoiceEntity.setCount(5);
        orderChoiceEntity.setMenuItemEntity(menuItemEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setTimeOpen(LocalDateTime.now());
        orderEntity.setTableNumber(2);
        orderEntity.setOrderChoiceEntities(Collections.singletonList(orderChoiceEntity));

        given(orderRepository.findByTableNumberAndTimeCloseIsNull(2)).willReturn(Optional.of(orderEntity));

        UserDiscountCardEntity userDiscountCardEntity = new UserDiscountCardEntity();
        userDiscountCardEntity.setId(1);
        userDiscountCardEntity.setAllSpentMoney(0D);
        userDiscountCardEntity.setClientDiscount(0D);
        userDiscountCardEntity.setName("Денис");

        given(userDiscountCardRepository.findById(1)).willReturn(Optional.of(userDiscountCardEntity));

        mockMvc.perform(post("/waiter/closeOrder").header("Authorization", token)
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

        mockMvc.perform(post("/waiter/closeOrder").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"tableNumber\" : 2,\n" +
                        "  \"clientId\" : 1\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

}