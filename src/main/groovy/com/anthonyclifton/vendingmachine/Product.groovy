package com.anthonyclifton.vendingmachine

enum Product {
    COLA(1.00),
    CHIPS(0.50),
    CANDY(0.65)

    BigDecimal cost

    Product(BigDecimal cost) {
        this.cost = cost
    }
}