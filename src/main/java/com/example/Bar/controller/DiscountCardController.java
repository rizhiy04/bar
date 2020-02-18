package com.example.Bar.controller;

import com.example.Bar.dto.discountCard.DiscountCardDTO;
import com.example.Bar.security.JwtUtil;
import com.example.Bar.service.DiscountCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/discount-cards")
public class DiscountCardController {

    private final JwtUtil jwtUtil;
    private final DiscountCardService discountCardService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DiscountCardDTO getDiscountCard(@RequestHeader("Authorization")final String authorizationHeader){
        return discountCardService.getDiscountCard(jwtUtil.extractUsername(authorizationHeader.substring(7)));
    }
}
