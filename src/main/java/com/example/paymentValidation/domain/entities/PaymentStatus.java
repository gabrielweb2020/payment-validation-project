package com.example.paymentValidation.domain.entities;

/**
 * Enum que representa os possíveis status de um pagamento.
 */
public enum PaymentStatus {
    /** Pagamento parcial. */
    PARTIAL,

    /** Pagamento completo. */
    FULL,

    /** Pagamento excedente. */
    EXCESS
}



