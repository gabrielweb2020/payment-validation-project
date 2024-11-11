package com.example.paymentValidation.domain.exceptions;

/**
 * Exceção personalizada para situações em que o status do pagamento é inválido.
 * Lançada quando o sistema encontra um status de pagamento que não é reconhecido ou permitido.
 */
public class InvalidPaymentStatusException extends RuntimeException {

    /**
     * Construtor que permite passar uma mensagem de erro detalhada.
     *
     * @param message a mensagem de erro explicativa.
     */
    public InvalidPaymentStatusException(String message) {
        super(message);
    }
}


