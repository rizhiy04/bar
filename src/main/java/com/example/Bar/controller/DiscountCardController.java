package com.example.Bar.controller;

import com.example.Bar.dto.discountCard.DiscountCardDTO;
import com.example.Bar.security.JwtUtil;
import com.example.Bar.service.DiscountCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/discount-cards")
@Api(value = "Discount system")
public class DiscountCardController {

    private final JwtUtil jwtUtil;
    private final DiscountCardService discountCardService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View information about user discount card", notes = "Use this method to get information about discount card")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get information"),
            @ApiResponse(code = 401, message = "You are not authorized"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public DiscountCardDTO getDiscountCard(final Authentication authentication){
        final String username = authentication.getName();
        return discountCardService.getDiscountCard(username);
    }
}
