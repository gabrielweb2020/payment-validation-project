package com.example.paymentValidation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração de segurança para habilitar autenticação e autorização
 * usando o filtro JWT e configurar políticas de sessão.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Construtor que injeta o filtro de autenticação JWT.
     *
     * @param jwtAuthenticationFilter filtro de autenticação JWT.
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura a cadeia de filtros de segurança e as políticas de acesso.
     *
     * @param http o objeto HttpSecurity para configuração.
     * @return a cadeia de filtros de segurança configurada.
     * @throws Exception se ocorrer algum erro na configuração de segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF para simplificar as requisições stateless
                .authorizeRequests(auth -> auth
                        .requestMatchers("/api/payments/**").authenticated() // Requer autenticação para a rota /api/payments/**
                        .anyRequest().permitAll() // Permite todas as outras requisições sem autenticação
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Define a sessão como stateless

        // Adiciona o filtro JWT na cadeia de filtros antes do filtro de autenticação padrão
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura o AuthenticationManager para ser usado no gerenciamento de autenticações.
     *
     * @param authConfig configuração de autenticação do Spring Security.
     * @return o AuthenticationManager configurado.
     * @throws Exception se ocorrer algum erro na criação do AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}



