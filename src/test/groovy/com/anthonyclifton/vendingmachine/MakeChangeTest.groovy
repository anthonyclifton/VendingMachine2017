package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

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
        vendingMachine.inventory = [(Product.CHIPS): 1, (Product.COLA): 1, (Product.CANDY): 1]

        vendingMachine.vault = [(Coin.QUARTER): 2,
                                (Coin.DIME): 1,
                                (Coin.NICKEL): 0]
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

    @Test
    void when_excess_money_is_entered_return_change_that_customer_didnt_insert() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)

        vendingMachine.pressButton(Product.CANDY)

        assert vendingMachine.dispenser.contains(Product.CANDY)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)

        assert 1 == vendingMachine.coinReturn.findAll { it == Coin.DIME }.size()
    }

    @Test
    void when_excess_money_is_entered_return_more_complex_change_that_customer_didnt_insert() {
        (1..5).each { vendingMachine.insertCoin(Coin.QUARTER) }
        (1..7).each { vendingMachine.insertCoin(Coin.NICKEL) }

        vendingMachine.pressButton(Product.CANDY)

        assert vendingMachine.dispenser.contains(Product.CANDY)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)

        assert 2 == vendingMachine.coinReturn.findAll { it == Coin.DIME }.size()
    }
}
