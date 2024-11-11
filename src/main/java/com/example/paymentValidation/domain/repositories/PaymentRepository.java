package com.example.paymentValidation.domain.repositories;

import com.example.paymentValidation.domain.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas à entidade Payment.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Busca um pagamento pelo código de cobrança.
     *
     * @param chargeCode o código de cobrança do pagamento.
     * @return um Optional contendo o pagamento encontrado, ou vazio se não encontrado.
     */
    Optional<Payment> findByChargeCode(String chargeCode);

    /**
     * Obtém o valor original do pagamento com base no código de cobrança.
     *
     * @param chargeCode o código de cobrança do pagamento.
     * @return o valor original do pagamento.
     */
    BigDecimal getOriginalAmountByChargeCode(String chargeCode);

    /**
     * Retorna uma página de pagamentos com base no objeto Pageable.
     *
     * @param pageable o objeto Pageable que define a paginação.
     * @return uma página contendo os pagamentos.
     */
    Page<Payment> findAll(Pageable pageable);
}



