package com.example.paymentValidation.domain.entities;

import java.time.LocalDateTime;

/**
 * Classe que representa uma entrada de auditoria para operações de pagamento.
 */
public class PaymentAudit {

    /** Tipo de operação realizada (ex.: validação, envio para fila). */
    private String operation;

    /** Detalhes adicionais sobre a operação. */
    private String details;

    /** Timestamp da operação. */
    private LocalDateTime timestamp;

    /**
     * Construtor que inicializa os dados da auditoria.
     *
     * @param operation o tipo de operação realizada.
     * @param details detalhes adicionais sobre a operação.
     */
    public PaymentAudit(String operation, String details) {
        this.operation = operation;
        this.details = details;
        this.timestamp = LocalDateTime.now(); // Define o horário atual automaticamente.
    }

    /**
     * Obtém o tipo de operação realizada.
     *
     * @return o tipo de operação.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Define o tipo de operação realizada.
     *
     * @param operation o tipo de operação.
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Obtém os detalhes da operação.
     *
     * @return os detalhes da operação.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Define os detalhes da operação.
     *
     * @param details os detalhes da operação.
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Obtém o timestamp da operação.
     *
     * @return o timestamp da operação.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Define o timestamp da operação.
     *
     * @param timestamp o timestamp da operação.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}



