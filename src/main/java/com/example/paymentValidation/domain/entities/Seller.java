package com.example.paymentValidation.domain.entities;

/**
 * Classe que representa um vendedor.
 */
public class Seller {

    /** Código do vendedor. */
    private String sellerCode;

    /**
     * Construtor que inicializa o código do vendedor.
     *
     * @param sellerCode o código do vendedor.
     */
    public Seller(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    /**
     * Obtém o código do vendedor.
     *
     * @return o código do vendedor.
     */
    public String getSellerCode() {
        return sellerCode;
    }

    /**
     * Define o código do vendedor.
     *
     * @param sellerCode o código do vendedor.
     */
    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }
}



