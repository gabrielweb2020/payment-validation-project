package com.example.paymentValidation.domain.entities;

import java.math.BigDecimal;

/**
 * Classe que representa um pagamento.
 */
public class Payment {

    /** Código de cobrança do pagamento. */
    private String chargeCode;

    /** Valor do pagamento. */
    private BigDecimal amount;

    /** Status do pagamento. */
    private String status;

    /**
     * Construtor para inicializar o pagamento com código de cobrança e valor.
     *
     * @param chargeCode o código de cobrança do pagamento.
     * @param amount o valor do pagamento.
     */
    public Payment(String chargeCode, BigDecimal amount) {
        this.chargeCode = chargeCode;
        this.amount = amount;
    }

    /**
     * Obtém o código de cobrança do pagamento.
     *
     * @return o código de cobrança.
     */
    public String getChargeCode() {
        return chargeCode;
    }

    /**
     * Define o código de cobrança do pagamento.
     *
     * @param chargeCode o código de cobrança.
     */
    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    /**
     * Obtém o valor do pagamento.
     *
     * @return o valor do pagamento.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Define o valor do pagamento.
     *
     * @param amount o valor do pagamento.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Obtém o status do pagamento.
     *
     * @return o status do pagamento.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status do pagamento.
     *
     * @param status o status do pagamento.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}


