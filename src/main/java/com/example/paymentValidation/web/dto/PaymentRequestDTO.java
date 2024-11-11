package com.example.paymentValidation.web.dto;

import java.math.BigDecimal;

/**
 * DTO para representar a solicitação de pagamento com dados necessários para processar um pagamento.
 */
public class PaymentRequestDTO {

    /** Código de cobrança do pagamento. */
    private String chargeCode;

    /** Valor do pagamento. */
    private BigDecimal amount;

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
}


