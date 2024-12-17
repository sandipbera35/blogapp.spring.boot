package com.app.blog.authenticator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AuthConfig {
    
    @Bean
    @Scope("prototype")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8091/api/v1") // Replace with your API base URL
                .build();
    }
}
