package com.anthonyclifton.vendingmachine

/**
 * Created by anthonyclifton on 4/5/17.
 */
enum Product {
    COLA(0.50)

    BigDecimal cost

    Product(BigDecimal cost) {
        this.cost = cost
    }
}