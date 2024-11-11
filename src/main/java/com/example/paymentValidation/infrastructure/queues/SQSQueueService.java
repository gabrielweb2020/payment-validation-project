package com.example.paymentValidation.infrastructure.queues;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.paymentValidation.domain.entities.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Serviço para enviar mensagens para uma fila SQS da AWS.
 */
@Service
public class SQSQueueService implements QueueService {

    private static final Logger logger = LoggerFactory.getLogger(SQSQueueService.class);
    private final AmazonSQS sqsClient;
    private final String queueUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Construtor que inicializa o cliente SQS e compõe a URL da fila usando valores configurados.
     *
     * @param queueName o nome da fila SQS.
     * @param region a região da AWS onde a fila está localizada.
     * @param accessKey a chave de acesso da AWS.
     * @param secretKey a chave secreta da AWS.
     * @param accountId o ID da conta AWS.
     */
    public SQSQueueService(
            @Value("${aws.sqs.queueName}") String queueName,
            @Value("${aws.sqs.region}") String region,
            @Value("${AWS_ACCESS_KEY_ID}") String accessKey,
            @Value("${AWS_SECRET_ACCESS_KEY}") String secretKey,
            @Value("${aws.sqs.accountId}") String accountId) {

        this.sqsClient = AmazonSQSClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        // Compor a URL da fila usando region, accountId e queueName
        this.queueUrl = "https://sqs." + region + ".amazonaws.com/" + accountId + "/" + queueName;
    }

    /**
     * Envia um pagamento para a fila SQS especificada.
     *
     * @param queueName o nome da fila para onde enviar a mensagem.
     * @param payment o objeto de pagamento a ser enviado.
     */
    @Override
    @CircuitBreaker(name = "queueService", fallbackMethod = "fallbackSendToQueue")
    @Retry(name = "queueService")
    public void sendToQueue(String queueName, Payment payment) {
        try {
            // Converte o objeto Payment para JSON para envio
            String messageBody = objectMapper.writeValueAsString(payment);

            // Configura o pedido de envio de mensagem para a fila
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);

            // Envia a mensagem para a fila SQS
            sqsClient.sendMessage(sendMessageRequest);
            logger.info("Mensagem enviada para a fila {}: {}", queueName, messageBody);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao converter o objeto Payment para JSON: {}", e.getMessage());
            throw new RuntimeException("Falha ao converter mensagem para JSON", e);
        }
    }

    /**
     * Método de fallback para caso o envio para a fila falhe.
     *
     * @param queueName o nome da fila para onde a mensagem deveria ter sido enviada.
     * @param payment o objeto de pagamento que falhou ao ser enviado.
     * @param t a exceção lançada durante a tentativa de envio.
     */
    public void fallbackSendToQueue(String queueName, Payment payment, Throwable t) {
        logger.error("Falha ao enviar para a fila {}. Mensagem de erro: {}", queueName, t.getMessage());
    }
}



