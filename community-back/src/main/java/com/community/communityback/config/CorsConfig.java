package com.community.communityback.config;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig{

    private final String DOMAIN = "http://localhost:3000/";
    
    @Bean
    public CorsFilter corsFilter() {

    
        List<String> allowedHeaders = new ArrayList<>();
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("Content-Language");
        allowedHeaders.add("Content-Encoding");
        allowedHeaders.add("Date");
        allowedHeaders.add("refresh_token");
        allowedHeaders.add("access_token");

        List<String> exposedHeaders = new ArrayList<>();
        exposedHeaders.add("refresh_token");
        exposedHeaders.add("access_token");

        Map<String, CorsConfiguration> corsConfigurations = new HashMap<>();
        

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
    
        config.setAllowCredentials(true);
        config.addAllowedOrigin(DOMAIN);
        config.setAllowedHeaders(allowedHeaders);
        config.addAllowedMethod("*");
        config.setExposedHeaders(exposedHeaders);
        source.registerCorsConfiguration("/api/**", config);
        
        corsConfigurations.put("/",config);
        corsConfigurations.put("/login",config);
        corsConfigurations.put("/join",config);
        corsConfigurations.put("/user/**", config);
        corsConfigurations.put("/board/**", config);
        corsConfigurations.put("/manager/**", config);
        corsConfigurations.put("/api/**", config);
        corsConfigurations.put("/images/**", config);
        corsConfigurations.put("/interest/**",config);

        source.setCorsConfigurations(corsConfigurations);

        return new CorsFilter(source);
        
    }

}
