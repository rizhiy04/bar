package com.example.Bar.service;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import com.example.Bar.entity.UserDiscountCard;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.repository.UserRepository;
import com.example.Bar.security.JwtUtil;
import com.example.Bar.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    //TODO I`ll change exception late
    public SignInResponse signUp(final SignUpRequestDTO signUpRequestDTO) throws Exception{
        if (userRepository.findByEmail(signUpRequestDTO.getEmail()) != null){
            throw new Exception("UserEntity already exist");
        }

        UserDiscountCard userDiscountCard = new UserDiscountCard();
        userDiscountCard.setName(signUpRequestDTO.getName());
        userDiscountCard.setClientDiscount(0d);
        userDiscountCard.setAllSpentMoney(0d);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpRequestDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        userEntity.setRoles(Roles.CLIENT);
        userEntity.setUserDiscountCard(userDiscountCard);

        userRepository.save(userEntity);

        return signIn(new SignInRequestDTO(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword()));
    }

    public SignInResponse signIn(final SignInRequestDTO signInRequestDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(), signInRequestDTO.getPassword()));

        return new SignInResponse(jwtUtil.generateToken(new User(signInRequestDTO.getEmail(), signInRequestDTO.getPassword(), List.of(new SimpleGrantedAuthority("CLIENT")))));
    }

}
