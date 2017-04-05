package com.anthonyclifton.vendingmachine

class VendingMachine {
    private static String INSERT_COIN = 'INSERT COIN'

    private static BigDecimal DIME_VALUE = 0.10
    private static BigDecimal QUARTER_VALUE = 0.25
    private static BigDecimal NICKEL_VALUE = 0.05

    BigDecimal currentAmount = 0.0
    List<Coin> coinReturn = []

    void insertCoin(Coin coin) {
        if (Coin.DIME.size == coin.size && Coin.DIME.weight == coin.weight) {
            currentAmount += DIME_VALUE
        } else if (Coin.QUARTER.size == coin.size && Coin.QUARTER.weight == coin.weight) {
            currentAmount += QUARTER_VALUE
        } else if (Coin.NICKEL.size == coin.size && Coin.NICKEL.weight == coin.weight) {
            currentAmount += NICKEL_VALUE
        } else {
            coinReturn << coin
        }
    }

    String getDisplay() {
        currentAmount ? currentAmount : INSERT_COIN
    }
}
