package com.example.paymentValidation.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para o ModelMapper, usada para mapeamento de objetos entre DTOs e entidades.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Define o ModelMapper como um bean para ser injetado em outras partes da aplicação.
     *
     * @return uma instância de ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}



