package com.example.paymentValidation.domain.exceptions;

/**
 * Exceção personalizada para situações em que o vendedor não é encontrado.
 * Lançada quando um código de vendedor específico não existe na base de dados.
 */
public class SellerNotFoundException extends RuntimeException {

    /**
     * Construtor que permite passar uma mensagem de erro detalhada.
     *
     * @param message a mensagem de erro explicativa.
     */
    public SellerNotFoundException(String message) {
        super(message);
    }
}


