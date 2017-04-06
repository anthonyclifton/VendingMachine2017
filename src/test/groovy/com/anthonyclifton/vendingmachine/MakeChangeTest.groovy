package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertEquals

/**
 * As a vendor I want customers to receive correct change so that they will use the vending machine again
 *
 * When a product is selected that costs less than the amount of money in the machine,
 * then the remaining amount is placed in the coin return.
 */

class MakeChangeTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()
    }

    @Test
    void when_excess_money_is_entered_return_the_difference() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)

        vendingMachine.pressButton(Product.COLA)

        assert vendingMachine.dispenser.contains(Product.COLA)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)

        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.QUARTER }.size()
    }

    @Test
    void when_excess_money_is_entered_return_the_difference_even_when_multiple_coins() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.DIME)
        vendingMachine.insertCoin(Coin.NICKEL)

        vendingMachine.pressButton(Product.CHIPS)

        assert vendingMachine.dispenser.contains(Product.CHIPS)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)

        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.QUARTER }.size()
        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.DIME }.size()
        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.NICKEL }.size()
    }
}
