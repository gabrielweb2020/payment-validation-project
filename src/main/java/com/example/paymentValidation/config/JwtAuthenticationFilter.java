package com.example.paymentValidation.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticação JWT para verificar e autenticar o usuário em cada requisição.
 * Implementa o filtro para ser executado uma vez por requisição.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Método para validar e extrair o nome de usuário (username) do token JWT.
     *
     * @param token o token JWT enviado na requisição.
     * @return o nome de usuário extraído do token se for válido; caso contrário, retorna null.
     */
    private String validateTokenAndGetUsername(String token) {
        // Aqui você deve implementar a validação e extração do usuário a partir do JWT
        // Retorne o username extraído do token se ele for válido; caso contrário, retorne null
        return "username"; // Substitua pela lógica de extração real
    }

    /**
     * Executa o filtro para verificar a validade do token JWT e autenticar o usuário.
     *
     * @param request  a requisição HTTP.
     * @param response a resposta HTTP.
     * @param filterChain a cadeia de filtros que deve ser seguida.
     * @throws ServletException se houver um erro de servlet.
     * @throws IOException se houver um erro de I/O.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Extrai o token do cabeçalho Authorization
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = validateTokenAndGetUsername(token);
        }

        // Se o token for válido, autentica o usuário no contexto de segurança
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Define o usuário autenticado no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}



