package com.example.Bar.service;

import com.example.Bar.dto.authentication.SignInRequestDTO;
import com.example.Bar.dto.authentication.SignInResponse;
import com.example.Bar.dto.authentication.SignUpRequestDTO;
import com.example.Bar.entity.UserDiscountCardEntity;
import com.example.Bar.entity.UserEntity;
import com.example.Bar.exception.SuchUserAlreadyExistException;
import com.example.Bar.exception.WrongPasswordException;
import com.example.Bar.repository.UserRepository;
import com.example.Bar.security.JwtUtil;
import com.example.Bar.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {

//    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void signUp(final SignUpRequestDTO signUpRequestDTO) throws SuchUserAlreadyExistException, UsernameNotFoundException, WrongPasswordException{

        if (userRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()){
            throw new SuchUserAlreadyExistException("User already exist");
        }

        final UserDiscountCardEntity discountCard = createUserDiscountCard(signUpRequestDTO);
        final UserEntity user = createUser(signUpRequestDTO, discountCard);
        userRepository.save(user);

//        return signIn(new SignInRequestDTO(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword()));
    }

    public SignInResponse signIn(final SignInRequestDTO signInRequestDTO) throws UsernameNotFoundException, WrongPasswordException {
        final UserEntity user = userRepository.findByEmail(signInRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such username"));

        if (!passwordEncoder.matches(signInRequestDTO.getPassword(), (user.getPassword())))
            throw new WrongPasswordException("Wrong password");

//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(), signInRequestDTO.getPassword()));

        return new SignInResponse(jwtUtil.generateToken(new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRoles().name())))));
    }

    private UserDiscountCardEntity createUserDiscountCard(final SignUpRequestDTO signUpRequestDTO){
        final UserDiscountCardEntity userDiscountCardEntity = new UserDiscountCardEntity();
        userDiscountCardEntity.setName(signUpRequestDTO.getName());
        userDiscountCardEntity.setClientDiscount(0d);
        userDiscountCardEntity.setAllSpentMoney(0d);

        return userDiscountCardEntity;
    }

    private UserEntity createUser(final SignUpRequestDTO signUpRequestDTO, final UserDiscountCardEntity userDiscountCardEntity){
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpRequestDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        userEntity.setRoles(Roles.CLIENT);
        userEntity.setUserDiscountCardEntity(userDiscountCardEntity);

        return userEntity;
    }

}
