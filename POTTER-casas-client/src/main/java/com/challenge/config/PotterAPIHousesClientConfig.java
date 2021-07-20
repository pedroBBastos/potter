package com.challenge.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PotterAPIHousesClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("apiKey", "c8158568-a5df-4c7b-a9a2-7d174dee0077");
    }
}
