package com.example.paymentValidation.application.strategy;

import com.example.paymentValidation.domain.entities.PaymentStatus;
import java.math.BigDecimal;

/**
 * Interface que define uma estratégia para determinar o status de pagamento
 * com base nos valores de pagamento e cobrança.
 */
public interface PaymentStatusStrategy {

    /**
     * Verifica se a estratégia corresponde ao pagamento,
     * com base na comparação entre o valor do pagamento e o valor original.
     *
     * @param paymentAmount valor do pagamento realizado.
     * @param originalAmount valor original da cobrança.
     * @return true se a estratégia corresponder ao pagamento, caso contrário false.
     */
    boolean matches(BigDecimal paymentAmount, BigDecimal originalAmount);

    /**
     * Obtém o status de pagamento associado à estratégia.
     *
     * @return o status de pagamento representado pela estratégia.
     */
    PaymentStatus getStatus();
}





