package com.challenge.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PotterAPIHousesClientConfig {

    @Value("${potterApi.apiKeyValue}")
    public String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("apiKey", apiKey);
    }
}
