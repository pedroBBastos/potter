package com.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * Classe de configuração contendo bean com ValidatorFactory default a ser usado pela aplicação.
 * @author PedroBastos
 */
@Configuration
public class ValidatorConfig {

    @Bean
    public ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }
}
