package com.example.paymentValidation.application.usecases;

import com.example.paymentValidation.application.service.PaymentStatusService;
import com.example.paymentValidation.infrastructure.queues.QueueService;
import com.example.paymentValidation.application.service.ValidationService;
import com.example.paymentValidation.domain.entities.Payment;
import com.example.paymentValidation.domain.entities.PaymentStatus;
import com.example.paymentValidation.domain.entities.Seller;
import com.example.paymentValidation.domain.repositories.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementação do caso de uso para validar e processar pagamentos.
 */
@Service
public class ValidateAndProcessPaymentUseCaseImpl implements ValidateAndProcessPaymentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ValidateAndProcessPaymentUseCaseImpl.class);

    private final ValidationService validationService;
    private final PaymentStatusService paymentStatusService;
    private final QueueService queueService;
    private final PaymentRepository paymentRepository;

    /**
     * Construtor que injeta os serviços e o repositório necessários.
     *
     * @param validationService serviço de validação.
     * @param paymentStatusService serviço para determinar o status de pagamento.
     * @param queueService serviço de fila para envio de mensagens.
     * @param paymentRepository repositório de pagamentos.
     */
    public ValidateAndProcessPaymentUseCaseImpl(ValidationService validationService,
                                                PaymentStatusService paymentStatusService,
                                                QueueService queueService,
                                                PaymentRepository paymentRepository) {
        this.validationService = validationService;
        this.paymentStatusService = paymentStatusService;
        this.queueService = queueService;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Obtém todos os pagamentos com suporte a paginação.
     *
     * @param pageable informações de paginação.
     * @return página contendo os pagamentos.
     */
    @Override
    public Page<Payment> getPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    /**
     * Executa a validação e processamento de uma lista de pagamentos.
     *
     * @param sellerCode código do vendedor.
     * @param payments lista de pagamentos a serem processados.
     */
    @Override
    public void execute(String sellerCode, List<Payment> payments) {
        logger.info("Iniciando validação para o vendedor: {}", sellerCode);

        Seller seller = validateSeller(sellerCode);

        for (Payment payment : payments) {
            processPayment(payment);
        }
    }

    /**
     * Valida a existência do vendedor.
     *
     * @param sellerCode código do vendedor.
     * @return objeto Seller, caso encontrado.
     */
    private Seller validateSeller(String sellerCode) {
        return validationService.validateSellerExists(sellerCode);
    }

    /**
     * Processa um pagamento individual, determinando seu status e enviando-o para a fila correspondente.
     *
     * @param payment pagamento a ser processado.
     */
    private void processPayment(Payment payment) {
        try {
            logger.info("Processando pagamento com código de cobrança: {}", payment.getChargeCode());

            BigDecimal originalAmount = validationService.validateChargeCodeAndReturnAmount(payment.getChargeCode());
            logger.info("Valor original obtido para o código {}: {}", payment.getChargeCode(), originalAmount);

            PaymentStatus status = paymentStatusService.determinePaymentStatus(payment.getAmount(), originalAmount);
            payment.setStatus(status.name());

            queueService.sendToQueue(status.name(), payment);
            logger.info("Pagamento {} processado e enviado para a fila: {}", payment.getChargeCode(), status.name());

        } catch (Exception e) {
            logger.error("Erro ao processar o pagamento {}: {}", payment.getChargeCode(), e.getMessage());
        }
    }
}




