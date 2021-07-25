package com.community.communityback.config;


import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.config.jwt.JwtAuthenticationFilter;
import com.community.communityback.config.jwt.JwtAuthorizationFilter;
import com.community.communityback.model.User;
import com.community.communityback.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    
    private final CorsFilter corsFilter;
    private final UserRepository userRepository;
    String USER = "hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')";
    String MANGER = "hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')";
    String ADMIN = "hasRole('ROLE_ADMIN')";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(corsFilter)
        .formLogin().disable()
        .httpBasic().disable()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
        .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/api/**").access(USER)
        .antMatchers("/manager/**").access(MANGER)
        .antMatchers("/admin/**").access(ADMIN)
        .anyRequest().permitAll();

        
    }

}
