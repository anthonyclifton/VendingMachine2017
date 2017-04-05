package com.anthonyclifton.vendingmachine

class VendingMachine {
    private static String INSERT_COIN = 'INSERT COIN'

    BigDecimal currentAmount = 0.0
    List<Coin> coinReturn = []

    void insertCoin(Coin coin) {
        if (coin.size == 1.0 && coin.weight == 2.0) {
            currentAmount += 0.10
        } else if (coin.size == 3.0 && coin.weight == 4.0) {
            currentAmount += 0.25
        } else if (coin.size == 5.0 && coin.weight == 6.0) {
            currentAmount += 0.05
        } else {
            coinReturn << coin
        }
    }

    String getDisplay() {
        currentAmount ? currentAmount : INSERT_COIN
    }
}