package com.anthonyclifton.vendingmachine

import java.math.MathContext

class VendingMachine {
    private static String INSERT_COIN = 'INSERT COIN'
    private static String THANK_YOU = 'THANK YOU'
    private static String SOLD_OUT = 'SOLD OUT'
    private static String EXACT_CHANGE = 'EXACT CHANGE ONLY'

    private static BigDecimal DIME_VALUE = 0.10
    private static BigDecimal QUARTER_VALUE = 0.25
    private static BigDecimal NICKEL_VALUE = 0.05

    DisplayState currentState = DisplayState.WAITING
    BigDecimal currentAmount = 0.0
    List<Coin> coinReturn = []
    List<Product> dispenser = []
    Product lastProduct
    Map<Product, Integer> inventory = [:]
    Map<Coin, Integer> vault = [:]


    // TODO: Add collector list and when we flush it, sort these into the vault by size and weight
    void insertCoin(Coin coin) {
        if (Coin.DIME.sizeInMillimeters == coin.sizeInMillimeters && Coin.DIME.weightInGrams == coin.weightInGrams) {
            currentAmount += DIME_VALUE
        } else if (Coin.QUARTER.sizeInMillimeters == coin.sizeInMillimeters && Coin.QUARTER.weightInGrams == coin.weightInGrams) {
            currentAmount += QUARTER_VALUE
        } else if (Coin.NICKEL.sizeInMillimeters == coin.sizeInMillimeters && Coin.NICKEL.weightInGrams == coin.weightInGrams) {
            currentAmount += NICKEL_VALUE
        } else {
            coinReturn << coin
        }
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

    private static List<Coin> makeChange(BigDecimal change) {
        List<Coin> coins = []

        int quarters = change / 0.25
        int dimes = (change - quarters * 0.25) / 0.10
        int nickels = (change - (quarters * 0.25) - (dimes * 0.10)) / 0.05

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
