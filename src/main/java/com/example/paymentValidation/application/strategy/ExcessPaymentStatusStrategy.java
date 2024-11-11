package com.example.paymentValidation.application.strategy;

import com.example.paymentValidation.domain.entities.PaymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Estratégia para determinar o status de pagamento como EXCESS.
 * Aplica-se quando o valor do pagamento é maior que o valor original da cobrança.
 */
@Component
public class ExcessPaymentStatusStrategy implements PaymentStatusStrategy {

    /**
     * Verifica se esta estratégia se aplica ao pagamento,
     * comparando o valor do pagamento com o valor original.
     *
     * @param paymentAmount valor do pagamento realizado.
     * @param originalAmount valor original da cobrança.
     * @return true se o valor do pagamento for maior que o valor original, caso contrário false.
     */
    @Override
    public boolean matches(BigDecimal paymentAmount, BigDecimal originalAmount) {
        return paymentAmount.compareTo(originalAmount) > 0;
    }

    /**
     * Retorna o status de pagamento associado a esta estratégia.
     *
     * @return PaymentStatus.EXCESS
     */
    @Override
    public PaymentStatus getStatus() {
        return PaymentStatus.EXCESS;
    }
}



