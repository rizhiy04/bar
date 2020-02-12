package com.example.Bar.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> inMemoryUsers = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final String password = inMemoryUsers.get(username);
        if (password == null) {
            throw new UsernameNotFoundException("User with email: " + username + " not found");
        } else {
            return new User(username, password, Collections.emptyList());
        }
    }

    public void saveUser(final String username, final String password) {
        inMemoryUsers.put(username, passwordEncoder.encode(password));
    }
}
