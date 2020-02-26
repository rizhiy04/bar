package com.example.Bar.service;

import com.example.Bar.converter.DiscountCardConverter;
import com.example.Bar.dto.discountCard.DiscountCardDTO;
import com.example.Bar.entity.UserDiscountCardEntity;
import com.example.Bar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DiscountCardService {

    private final UserRepository userRepository;
    private final DiscountCardConverter discountCardConverter;

    public DiscountCardDTO getDiscountCard(final String email){
        final UserDiscountCardEntity  discountCard = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No such username")).getUserDiscountCardEntity();

        return discountCardConverter.convertToDTO(discountCard);
    }
}
