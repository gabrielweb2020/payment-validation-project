package com.example.paymentValidation.application.service;

import com.example.paymentValidation.domain.entities.Seller;
import com.example.paymentValidation.domain.entities.Payment;
import com.example.paymentValidation.domain.exceptions.PaymentNotFoundException;
import com.example.paymentValidation.domain.exceptions.SellerNotFoundException;
import com.example.paymentValidation.domain.repositories.PaymentRepository;
import com.example.paymentValidation.domain.repositories.SellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Serviço para validar a existência de vendedor e código de cobrança.
 */
@Service
public class ValidationService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);
    private final SellerRepository sellerRepository;
    private final PaymentRepository paymentRepository;

    /**
     * Construtor que injeta os repositórios de vendedor e pagamento.
     *
     * @param sellerRepository repositório de vendedores.
     * @param paymentRepository repositório de pagamentos.
     */
    public ValidationService(SellerRepository sellerRepository, PaymentRepository paymentRepository) {
        this.sellerRepository = sellerRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Verifica se o vendedor existe na base de dados e usa cache para otimizar a consulta.
     *
     * @param sellerCode código do vendedor.
     * @return objeto Seller, caso encontrado.
     * @throws SellerNotFoundException se o vendedor não for encontrado.
     */
    @Cacheable(value = "sellerCache", key = "#sellerCode")
    public Seller validateSellerExists(String sellerCode) {
        logger.info("Validando existência do vendedor com código: {}", sellerCode);
        return sellerRepository.findBySellerCode(sellerCode)
                .orElseThrow(() -> new SellerNotFoundException("Vendedor não encontrado"));
    }

    /**
     * Verifica se o código de cobrança existe e retorna o valor original da cobrança.
     *
     * @param chargeCode código da cobrança.
     * @return valor original da cobrança.
     * @throws PaymentNotFoundException se o código de cobrança não for encontrado.
     */
    public BigDecimal validateChargeCodeAndReturnAmount(String chargeCode) {
        logger.info("Validando existência do código de cobrança: {}", chargeCode);
        Payment payment = paymentRepository.findByChargeCode(chargeCode)
                .orElseThrow(() -> new PaymentNotFoundException("Código de cobrança não encontrado"));
        return payment.getAmount();
    }
}

