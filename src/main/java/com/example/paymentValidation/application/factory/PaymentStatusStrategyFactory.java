package com.example.paymentValidation.application.factory;

import com.example.paymentValidation.application.strategy.FullPaymentStatusStrategy;
import com.example.paymentValidation.application.strategy.PartialPaymentStatusStrategy;
import com.example.paymentValidation.application.strategy.ExcessPaymentStatusStrategy;
import com.example.paymentValidation.application.strategy.PaymentStatusStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Fábrica para selecionar a estratégia correta de status de pagamento com base no valor.
 */
@Component
public class PaymentStatusStrategyFactory {

    private final FullPaymentStatusStrategy fullStrategy;
    private final PartialPaymentStatusStrategy partialStrategy;
    private final ExcessPaymentStatusStrategy excessStrategy;

    /**
     * Construtor que injeta todas as estratégias possíveis.
     *
     * @param fullStrategy    estratégia para pagamento completo.
     * @param partialStrategy estratégia para pagamento parcial.
     * @param excessStrategy  estratégia para pagamento excedente.
     */
    public PaymentStatusStrategyFactory(FullPaymentStatusStrategy fullStrategy,
                                        PartialPaymentStatusStrategy partialStrategy,
                                        ExcessPaymentStatusStrategy excessStrategy) {
        this.fullStrategy = fullStrategy;
        this.partialStrategy = partialStrategy;
        this.excessStrategy = excessStrategy;
    }

    /**
     * Retorna a estratégia apropriada com base nos valores de pagamento.
     *
     * @param paymentAmount  valor do pagamento realizado.
     * @param originalAmount valor original da cobrança.
     * @return estratégia de status de pagamento apropriada.
     */
    public PaymentStatusStrategy determinePaymentStatusStrategy(BigDecimal paymentAmount, BigDecimal originalAmount) {
        if (paymentAmount.compareTo(originalAmount) < 0) {
            return partialStrategy;
        } else if (paymentAmount.compareTo(originalAmount) == 0) {
            return fullStrategy;
        } else {
            return excessStrategy;
        }
    }
}

