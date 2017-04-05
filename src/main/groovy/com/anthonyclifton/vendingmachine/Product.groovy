package com.anthonyclifton.vendingmachine

/**
 * Created by anthonyclifton on 4/5/17.
 */
enum Product {
    COLA(1.00),
    CHIPS(0.50),
    CANDY(0.65)

    BigDecimal cost

    Product(BigDecimal cost) {
        this.cost = cost
    }
}