package com.example.paymentValidation.application.usecases;

import com.example.paymentValidation.application.service.PaymentStatusService;
import com.example.paymentValidation.infrastructure.queues.QueueService;
import com.example.paymentValidation.application.service.ValidationService;
import com.example.paymentValidation.domain.entities.Payment;
import com.example.paymentValidation.domain.entities.PaymentStatus;
import com.example.paymentValidation.domain.entities.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * Testes para o caso de uso ValidateAndProcessPaymentUseCase.
 */
public class ValidateAndProcessPaymentUseCaseTest {

    @Mock
    private ValidationService validationService;

    @Mock
    private PaymentStatusService paymentStatusService;

    @Mock
    private QueueService queueService;

    @InjectMocks
    private ValidateAndProcessPaymentUseCaseImpl validateAndProcessPaymentUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o processamento de um pagamento válido.
     */
    @Test
    public void testExecute_withValidData_shouldProcessPayment() {
        // Configura mocks para simular os métodos chamados durante a execução do caso de uso
        when(validationService.validateSellerExists("123")).thenReturn(new Seller("123"));
        Payment payment = new Payment("C1", new BigDecimal("100"));

        // Atualiza a chamada ao novo método para obter o valor do pagamento com o código da cobrança
        when(validationService.validateChargeCodeAndReturnAmount("C1")).thenReturn(new BigDecimal("100"));
        when(paymentStatusService.determinePaymentStatus(new BigDecimal("100"), new BigDecimal("100")))
                .thenReturn(PaymentStatus.FULL);

        // Executa o caso de uso
        validateAndProcessPaymentUseCase.execute("123", Collections.singletonList(payment));

        // Verifica se o pagamento foi enviado para a fila com o status FULL
        verify(queueService, times(1)).sendToQueue("FULL", payment);
    }
}
