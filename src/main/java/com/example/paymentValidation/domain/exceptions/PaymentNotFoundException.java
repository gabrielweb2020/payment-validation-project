package com.example.paymentValidation.domain.exceptions;

/**
 * Exceção personalizada para situações em que o pagamento não é encontrado.
 * Lançada quando um código de pagamento específico não existe na base de dados.
 */
public class PaymentNotFoundException extends RuntimeException {

    /**
     * Construtor que permite passar uma mensagem de erro detalhada.
     *
     * @param message a mensagem de erro explicativa.
     */
    public PaymentNotFoundException(String message) {
        super(message);
    }
}


