package com.cvi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OAuthConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
