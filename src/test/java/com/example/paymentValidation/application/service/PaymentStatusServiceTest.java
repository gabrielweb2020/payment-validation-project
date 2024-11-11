package com.example.paymentValidation.application.service;

import com.example.paymentValidation.application.factory.PaymentStatusStrategyFactory;
import com.example.paymentValidation.application.strategy.PaymentStatusStrategy;
import com.example.paymentValidation.domain.entities.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Testes para a classe PaymentStatusService.
 */
public class PaymentStatusServiceTest {

    @Mock
    private PaymentStatusStrategyFactory strategyFactory;

    @InjectMocks
    private PaymentStatusService paymentStatusService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa a determinação do status de pagamento parcial.
     */
    @Test
    public void testDeterminePaymentStatusPartial() {
        PaymentStatusStrategy partialStrategy = new PaymentStatusStrategy() {
            @Override
            public boolean matches(BigDecimal paymentAmount, BigDecimal originalAmount) {
                return true;
            }

            @Override
            public PaymentStatus getStatus() {
                return PaymentStatus.PARTIAL;
            }
        };

        when(strategyFactory.determinePaymentStatusStrategy(new BigDecimal("50"), new BigDecimal("100"))).thenReturn(partialStrategy);
        assertEquals(PaymentStatus.PARTIAL, paymentStatusService.determinePaymentStatus(new BigDecimal("50"), new BigDecimal("100")));
    }

    /**
     * Testa a determinação do status de pagamento completo.
     */
    @Test
    public void testDeterminePaymentStatusFull() {
        PaymentStatusStrategy fullStrategy = new PaymentStatusStrategy() {
            @Override
            public boolean matches(BigDecimal paymentAmount, BigDecimal originalAmount) {
                return true;
            }

            @Override
            public PaymentStatus getStatus() {
                return PaymentStatus.FULL;
            }
        };

        when(strategyFactory.determinePaymentStatusStrategy(new BigDecimal("100"), new BigDecimal("100"))).thenReturn(fullStrategy);
        assertEquals(PaymentStatus.FULL, paymentStatusService.determinePaymentStatus(new BigDecimal("100"), new BigDecimal("100")));
    }

    /**
     * Testa a determinação do status de pagamento excedente.
     */
    @Test
    public void testDeterminePaymentStatusExcess() {
        PaymentStatusStrategy excessStrategy = new PaymentStatusStrategy() {
            @Override
            public boolean matches(BigDecimal paymentAmount, BigDecimal originalAmount) {
                return true;
            }

            @Override
            public PaymentStatus getStatus() {
                return PaymentStatus.EXCESS;
            }
        };

        when(strategyFactory.determinePaymentStatusStrategy(new BigDecimal("150"), new BigDecimal("100"))).thenReturn(excessStrategy);
        assertEquals(PaymentStatus.EXCESS, paymentStatusService.determinePaymentStatus(new BigDecimal("150"), new BigDecimal("100")));
    }
}
