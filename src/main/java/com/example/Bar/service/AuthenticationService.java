package com.example.Bar.service;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import com.example.Bar.entity.Permission;
import com.example.Bar.entity.UserDiscountCard;
import com.example.Bar.repository.UserDiscountCardRepository;
import com.example.Bar.repository.UserRepository;
import com.example.Bar.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final UserDiscountCardRepository userDiscountCardRepository;

    //TODO I`ll change exception late
    public SignInResponse signUp(final SignUpRequestDTO signUpRequestDTO) throws Exception{
        if (userRepository.findByEmail(signUpRequestDTO.getEmail()) != null){
            throw new Exception("User already exist");
        }

        UserDiscountCard userDiscountCard = new UserDiscountCard();
        userDiscountCard.setName(signUpRequestDTO.getName());
        userDiscountCard.setClientDiscount(0d);
        userDiscountCard.setAllSpentMoney(0d);

        com.example.Bar.entity.User user = new com.example.Bar.entity.User();
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(signUpRequestDTO.getPassword());
        user.setPermission(Permission.ClIENT);
        user.setUserDiscountCard(userDiscountCard);

        userDiscountCard.setUser(user);
        userDiscountCardRepository.save(userDiscountCard);

        userRepository.save(user);

        return signIn(new SignInRequestDTO(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword()));
    }

    public SignInResponse signIn(final SignInRequestDTO signInRequestDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(), signInRequestDTO.getPassword()));

        return new SignInResponse(jwtUtil.generateToken(new User(signInRequestDTO.getEmail(), signInRequestDTO.getPassword(), List.of(new SimpleGrantedAuthority("CLIENT")))));
    }

}
