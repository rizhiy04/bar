package com.example.Bar.controller;

import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class DiscountCardControllerTest extends AbstractControllerTest{

    @Test
    public void testGetDiscountCardIsOk() throws Exception{
        final String token = signIn(Roles.CLIENT);

        mockMvc.perform(get("/discount-cards").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"email\" : \"client@gmail.com\",\n" +
                        "  \"discount\" : 0.0,\n" +
                        "  \"allSpentMoney\" : 0.0\n" +
                        "}"));
    }

    @Test
    public void testGetDiscountCardAccessDenied() throws Exception{
        mockMvc.perform(get("/discount-cards"))
                .andExpect(status().isForbidden());
    }

}