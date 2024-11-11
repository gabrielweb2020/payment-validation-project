package com.example.paymentValidation.application.usecases;

import com.example.paymentValidation.domain.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface para o caso de uso de validação e processamento de pagamentos.
 */
public interface ValidateAndProcessPaymentUseCase {

    /**
     * Obtém todos os pagamentos com suporte a paginação.
     *
     * @param pageable informações de paginação
     * @return página contendo os pagamentos
     */
    Page<Payment> getPayments(Pageable pageable);

    /**
     * Executa a validação e processamento de uma lista de pagamentos.
     *
     * @param sellerCode código do vendedor
     * @param payments lista de pagamentos a serem processados
     */
    void execute(String sellerCode, List<Payment> payments);
}




