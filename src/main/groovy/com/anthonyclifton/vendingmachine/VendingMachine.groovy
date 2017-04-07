package com.anthonyclifton.vendingmachine

import java.math.MathContext

class VendingMachine {
    private static final String INSERT_COIN = 'INSERT COIN'
    private static final String THANK_YOU = 'THANK YOU'
    private static final String SOLD_OUT = 'SOLD OUT'
    private static final String EXACT_CHANGE = 'EXACT CHANGE ONLY'

    private static final BigDecimal DIME_SIZE_MM = 17.91
    private static final BigDecimal DIME_WEIGHT_GRAMS = 2.268

    private static final BigDecimal QUARTER_SIZE_MM = 24.26
    private static final BigDecimal QUARTER_WEIGHT_GRAMS = 5.67

    private static final BigDecimal NICKEL_SIZE_MM = 21.21
    private static final BigDecimal NICKEL_WEIGHT_GRAMS = 5.0

    private static final BigDecimal DIME_VALUE = 0.10
    private static final BigDecimal QUARTER_VALUE = 0.25
    private static final BigDecimal NICKEL_VALUE = 0.05

    private DisplayState currentState = DisplayState.WAITING
    private BigDecimal currentAmount = 0.0
    List<Coin> coinReturn = []
    List<Product> dispenser = []
    private Product lastProduct
    private Map<Product, Integer> inventory = [:]
    private Map<Coin, Integer> vault = [:]


    void insertCoin(Coin coin) {
        BigDecimal coinValue = getCoinValue(coin)
        if (coinValue == 0.00) coinReturn << coin
        currentAmount += coinValue
    }

    void pressButton(Product product) {
        if (inventory[product] < 1) {
            currentState = DisplayState.SOLD_OUT
        } else if (product.cost <= currentAmount) {
            dispenser << product
            currentState = DisplayState.DISPENSED
            coinReturn.addAll(makeChange(currentAmount - product.cost))
            currentAmount = 0.0
        } else {
            lastProduct = product
            currentState = DisplayState.PRICE
        }
    }

    void returnCoins() {
        coinReturn.addAll(makeChange(currentAmount))
        currentAmount = 0.0
    }

    String getDisplay() {
        switch (currentState) {
            case DisplayState.WAITING:
                return currentAmount ? currentAmount : (isExactChangeRequired() ? EXACT_CHANGE : INSERT_COIN)
                break
            case DisplayState.DISPENSED:
                currentState = DisplayState.WAITING
                return THANK_YOU
                break
            case DisplayState.PRICE:
                currentState = DisplayState.WAITING
                return "PRICE ${lastProduct.cost}"
                break
            case DisplayState.SOLD_OUT:
                currentState = DisplayState.WAITING
                return SOLD_OUT
                break
        }
    }

    private static BigDecimal getCoinValue(Coin coin) {
        if (coin.sizeInMillimeters == NICKEL_SIZE_MM && coin.weightInGrams == NICKEL_WEIGHT_GRAMS) return NICKEL_VALUE
        if (coin.sizeInMillimeters == DIME_SIZE_MM && coin.weightInGrams == DIME_WEIGHT_GRAMS) return DIME_VALUE
        if (coin.sizeInMillimeters == QUARTER_SIZE_MM && coin.weightInGrams == QUARTER_WEIGHT_GRAMS) return QUARTER_VALUE
        return 0.00
    }

    private static List<Coin> makeChange(BigDecimal change) {
        List<Coin> coins = []

        int quarters = change / QUARTER_VALUE
        int dimes = (change - quarters * QUARTER_VALUE) / DIME_VALUE
        int nickels = (change - (quarters * QUARTER_VALUE) - (dimes * DIME_VALUE)) / NICKEL_VALUE

        if (quarters) (1..quarters).each { coins.add(Coin.QUARTER) }
        if (dimes) (1..dimes).each { coins.add(Coin.DIME) }
        if (nickels) (1..nickels).each { coins.add(Coin.NICKEL) }

        return coins
    }

    private boolean isExactChangeRequired() {
        Product.values().find { Product product ->
            BigDecimal change = (Math.ceil(product.cost) - product.cost)
            List<Coin> changeCoins = makeChange(change.round(new MathContext(2)))
            if (vault[Coin.QUARTER] < changeCoins.findAll { it == Coin.QUARTER}.size()) return true
            if (vault[Coin.DIME] < changeCoins.findAll { it == Coin.DIME}.size()) return true
            if (vault[Coin.NICKEL] < changeCoins.findAll { it == Coin.NICKEL}.size()) return true
            false
        }
    }
}
