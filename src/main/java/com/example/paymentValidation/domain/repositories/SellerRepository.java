package com.example.paymentValidation.domain.repositories;

import com.example.paymentValidation.domain.entities.Seller;
import java.util.Optional;

/**
 * Repositório para operações de banco de dados relacionadas à entidade Seller.
 */
public interface SellerRepository {

    /**
     * Busca um vendedor pelo código do vendedor.
     *
     * @param sellerCode o código do vendedor.
     * @return um Optional contendo o vendedor encontrado, ou vazio se não encontrado.
     */
    Optional<Seller> findBySellerCode(String sellerCode);
}



