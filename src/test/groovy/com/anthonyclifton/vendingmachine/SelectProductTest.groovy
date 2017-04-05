package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * As a vendor I want customers to select products so that I can give them an incentive to put money in the machine
 *
 * There are three products: cola for $1.00, chips for $0.50, and candy for $0.65.
 * When the respective button is pressed and enough money has been inserted, the product is dispensed
 * and the machine displays THANK YOU.  If the display is checked again, it will display INSERT COIN
 * and the current amount will be set to $0.00.  If there is not enough money inserted then the machine
 * displays PRICE and the price of the item and subsequent checks of the display will display
 * either INSERT COIN or the current amount as appropriate.
 */

class SelectProductTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()
    }

    @Test
    void cola_vended_when_adequate_money_is_inserted() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.pressButton(Product.COLA)

        assert vendingMachine.dispenser.contains(Product.COLA)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)
    }

    @Test
    void chips_vended_when_adequate_money_is_inserted() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.pressButton(Product.CHIPS)

        assert vendingMachine.dispenser.contains(Product.CHIPS)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)

    }

    @Test
    void candy_vended_when_adequate_money_is_inserted() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.DIME)
        vendingMachine.insertCoin(Coin.NICKEL)
        vendingMachine.pressButton(Product.CANDY)

        assert vendingMachine.dispenser.contains(Product.CANDY)
        assertEquals('THANK YOU', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
        assertEquals(0.0, vendingMachine.currentAmount)
    }
}
