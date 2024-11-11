package com.example.paymentValidation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração para a documentação da API com Swagger/OpenAPI.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura o Swagger/OpenAPI com título, versão e descrição.
     *
     * @return configuração do OpenAPI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Payment Validation API")
                .version("1.0.0")
                .description("Documentação da API para validação e processamento de pagamentos"));
    }
}



