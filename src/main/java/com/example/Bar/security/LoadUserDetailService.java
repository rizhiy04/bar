package com.example.Bar.security;

import com.example.Bar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        com.example.Bar.entity.User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + username + " not found");
        } else {
            return new User(username, user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getPermission().name())));
        }
    }
}
