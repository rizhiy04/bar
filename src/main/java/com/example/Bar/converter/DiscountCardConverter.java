package com.example.Bar.converter;

import com.example.Bar.dto.discountCard.DiscountCardDTO;
import com.example.Bar.entity.UserDiscountCardEntity;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardConverter {

    public DiscountCardDTO convertToDTO(final UserDiscountCardEntity userDiscountCardEntity){
        final DiscountCardDTO discountCardDTO = new DiscountCardDTO();
        discountCardDTO.setName(userDiscountCardEntity.getName());
        discountCardDTO.setEmail(userDiscountCardEntity.getUserEntity().getEmail());
        discountCardDTO.setAllSpentMoney(userDiscountCardEntity.getAllSpentMoney().doubleValue());
        discountCardDTO.setDiscount(userDiscountCardEntity.getClientDiscount());

        return discountCardDTO;
    }
}
