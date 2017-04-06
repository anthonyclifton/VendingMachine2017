package com.anthonyclifton.vendingmachine

class VendingMachine {
    private static String INSERT_COIN = 'INSERT COIN'
    private static String THANK_YOU = 'THANK YOU'

    private static BigDecimal DIME_VALUE = 0.10
    private static BigDecimal QUARTER_VALUE = 0.25
    private static BigDecimal NICKEL_VALUE = 0.05

    DisplayState currentState = DisplayState.WAITING
    BigDecimal currentAmount = 0.0
    List<Coin> coinReturn = []
    List<Product> dispenser = []
    Product lastProduct

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
        if (product.cost <= currentAmount) {
            dispenser << product
            currentState = DisplayState.DISPENSED
            currentAmount = 0.0
            coinReturn.addAll(makeChange(currentAmount - product.cost))
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
                return currentAmount ? currentAmount : INSERT_COIN
                break
            case DisplayState.DISPENSED:
                currentState = DisplayState.WAITING
                return THANK_YOU
            case DisplayState.PRICE:
                currentState = DisplayState.WAITING
                return "PRICE ${lastProduct.cost}"
        }
    }

    private static List<Coin> makeChange(BigDecimal change) {
            List<Coin> coins = []

            int quarters = change / 0.25
            int dimes = (change - quarters * 0.25) / 0.10
            int nickels = (change - (quarters * 0.25) - (dimes * 0.10)) / 0.05

            [1..quarters].each { coins.add(Coin.QUARTER) }
            [1..dimes].each { coins.add(Coin.DIME) }
            [1..nickels].each { coins.add(Coin.NICKEL) }

            return coins
    }
}
