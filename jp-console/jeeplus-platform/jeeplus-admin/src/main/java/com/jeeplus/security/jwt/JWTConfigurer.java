package com.jeeplus.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter <DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public JWTConfigurer(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(HttpSecurity http) {
        JWTFilter customFilter = new JWTFilter(tokenProvider, authenticationManager);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
