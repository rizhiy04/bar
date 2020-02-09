package com.example.Bar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WaiterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetReservationIsOk() throws Exception{
        mockMvc.perform(get("/waiter/reservation"))
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
    public void testGetFreeTablesIsOk() throws Exception{
        mockMvc.perform(get("/waiter/freeTables/2"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"tableNumbers\" : [1, 3]\n" +
                        "}\n"));
    }

    @Test
    public void testNewOrderIsCreated() throws Exception{
        mockMvc.perform(post("/waiter/makeOrder")
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
    public void testCloseOrderIsOk() throws Exception{
        mockMvc.perform(post("/waiter/closeOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"tableNumber\" : 2,\n" +
                                "  \"clientId\" : 1\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"response\" : \"Заказ закрыт, к оплате 25р\"\n" +
                        "}"));
    }

}