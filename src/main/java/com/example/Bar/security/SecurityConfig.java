package com.example.Bar.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoadUserDetailService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/inventories", "/orders/**").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/events", "/inventories", "/menu").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, "/inventories").hasRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/events/*", "/inventories/*", "/menu/*").hasRole(Roles.ADMIN.name())

                .antMatchers(HttpMethod.GET, "/reservations/**").hasRole(Roles.WAITER.name())
                .antMatchers(HttpMethod.POST, "/orders").hasRole(Roles.WAITER.name())
                .antMatchers(HttpMethod.PATCH, "/orders").hasRole(Roles.WAITER.name())

                .antMatchers(HttpMethod.GET, "/discount-cards").authenticated()

                .antMatchers(HttpMethod.GET, "/menu/*", "/events").permitAll()
                .antMatchers(HttpMethod.POST, "/reservations", "/sign-up", "/sign-in").permitAll()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
