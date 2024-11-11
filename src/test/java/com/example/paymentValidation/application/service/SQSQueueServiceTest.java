package com.example.paymentValidation.application.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.paymentValidation.domain.entities.Payment;
import com.example.paymentValidation.infrastructure.queues.SQSQueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * Testes para o serviço de envio de mensagens SQS.
 */
public class SQSQueueServiceTest {

    @Mock
    private AmazonSQS amazonSQS;

    @InjectMocks
    private SQSQueueService sqsQueueService;

    private Payment payment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment("C1", new BigDecimal("100"));
    }

    /**
     * Testa o envio de mensagem para a fila SQS.
     */
    @Test
    public void testSendToQueue() {
        doNothing().when(amazonSQS).sendMessage(any(SendMessageRequest.class));

        sqsQueueService.sendToQueue("FULL", payment);

        ArgumentCaptor<SendMessageRequest> captor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(amazonSQS, times(1)).sendMessage(captor.capture());

        SendMessageRequest sentRequest = captor.getValue();
        String expectedMessageBody = payment.toString();
    }
}
