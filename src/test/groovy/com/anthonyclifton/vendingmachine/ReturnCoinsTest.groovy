package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * As a customer I want to have my money returned so that I can change my mind about buying stuff
 * from the vending machine
 *
 * When the return coins button is pressed, the money the customer has placed in the machine
 * is returned and the display shows INSERT COIN.
 */

class ReturnCoinsTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()

        vendingMachine.vault = [(Coin.QUARTER): 0,
                                (Coin.DIME): 2,
                                (Coin.NICKEL): 1]
    }

    @Test
    void when_excess_money_is_entered_return_the_difference() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.DIME)
        vendingMachine.insertCoin(Coin.NICKEL)

        vendingMachine.returnCoins()

        assertEquals('INSERT COIN', vendingMachine.getDisplay())

        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.QUARTER }.size()
        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.DIME }.size()
        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.NICKEL }.size()
    }
}
