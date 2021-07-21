package com.challenge.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração contendo bean com ModelMapper default a ser usado pela aplicação.
 * @author PedroBastos
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
