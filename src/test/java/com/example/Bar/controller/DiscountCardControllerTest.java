package com.example.Bar.controller;

import com.example.Bar.entity.UserDiscountCardEntity;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.security.Roles;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class DiscountCardControllerTest extends AbstractControllerTest{

    @Test
    public void testGetDiscountCardIsOk() throws Exception{
        //given
        final String token = signIn(Roles.CLIENT);
        final UserEntity testUserEntity = getUserEntity();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(testUserEntity));

        //when
        mockMvc.perform(get("/discount-cards").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"name\" : \"Денис\",\n" +
                        "  \"email\" : \"client@gmail.com\",\n" +
                        "  \"discount\" : 0.0,\n" +
                        "  \"allSpentMoney\" : 0.0\n" +
                        "}"));

        verify(userRepository, times(3)).findByEmail(anyString());
    }

    @Test
    public void testGetDiscountCardAccessDenied() throws Exception{
        mockMvc.perform(get("/discount-cards"))
                .andExpect(status().isForbidden());
    }

    private UserEntity getUserEntity(){
        final UserDiscountCardEntity userDiscountCardEntity = getUserDiscountCardEntity();
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail("client@gmail.com");
        userEntity.setPassword("$2a$10$X3DIONmNXM1Rs/CExdtt3efVJbX5j8jYRDkos2EcY.cqB7zwFvBFu");
        userEntity.setRole(Roles.CLIENT);
        userEntity.setUserDiscountCardEntity(userDiscountCardEntity);
        userDiscountCardEntity.setUserEntity(userEntity);

        return userEntity;
    }

    private UserDiscountCardEntity getUserDiscountCardEntity(){
        final UserDiscountCardEntity userDiscountCardEntity = new UserDiscountCardEntity();
        userDiscountCardEntity.setClientDiscount(0.00D);
        userDiscountCardEntity.setAllSpentMoney(BigDecimal.valueOf(0));
        userDiscountCardEntity.setName("Денис");

        return userDiscountCardEntity;
    }

}