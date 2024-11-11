package com.example.paymentValidation.application.service;

import com.example.paymentValidation.application.factory.PaymentStatusStrategyFactory;
import com.example.paymentValidation.application.strategy.PaymentStatusStrategy;
import com.example.paymentValidation.domain.entities.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Serviço para determinar o status de um pagamento usando uma fábrica de estratégias.
 */
@Service
public class PaymentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatusService.class);
    private final PaymentStatusStrategyFactory strategyFactory;

    /**
     * Construtor que injeta a fábrica de estratégias.
     *
     * @param strategyFactory fábrica de estratégias de status de pagamento.
     */
    public PaymentStatusService(PaymentStatusStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    /**
     * Determina o status de um pagamento com base no valor pago e no valor original.
     *
     * @param paymentAmount  valor do pagamento realizado.
     * @param originalAmount valor original da cobrança.
     * @return status do pagamento.
     */
    public PaymentStatus determinePaymentStatus(BigDecimal paymentAmount, BigDecimal originalAmount) {
        logger.info("Determinando status para paymentAmount: {} e originalAmount: {}", paymentAmount, originalAmount);
        PaymentStatusStrategy strategy = strategyFactory.determinePaymentStatusStrategy(paymentAmount, originalAmount);
        return strategy.getStatus();
    }
}

