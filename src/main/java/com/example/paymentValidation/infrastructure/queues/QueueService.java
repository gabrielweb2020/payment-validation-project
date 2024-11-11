package com.example.paymentValidation.infrastructure.queues;

import com.example.paymentValidation.domain.entities.Payment;

/**
 * Interface para enviar mensagens para uma fila.
 */
public interface QueueService {

    /**
     * Envia um pagamento para uma fila específica.
     *
     * @param queueName o nome da fila para onde enviar o pagamento.
     * @param payment o objeto de pagamento a ser enviado.
     */
    void sendToQueue(String queueName, Payment payment);
}



