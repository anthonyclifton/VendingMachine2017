package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * As a customer I want to be told when the item I have selected is not available ao that I can select another item
 *
 * When the item selected by the customer is out of stock, the machine displays SOLD OUT.
 * If the display is checked again, it will display the amount of money remaining in the machine
 * or INSERT COIN if there is no money in the machine.
 */

class SoldOutTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()
        vendingMachine.inventory = [(Product.CHIPS): 0, (Product.COLA): 1, (Product.CANDY): 1]

        vendingMachine.vault = [(Coin.QUARTER): 0,
                                (Coin.DIME): 2,
                                (Coin.NICKEL): 1]
    }

    @Test
    void when_no_inventory_display_soldout() {
        vendingMachine.insertCoin(Coin.QUARTER)
        vendingMachine.insertCoin(Coin.QUARTER)

        vendingMachine.pressButton(Product.CHIPS)

        assertEquals('SOLD OUT', vendingMachine.getDisplay())
        assertEquals('0.50', vendingMachine.getDisplay())
    }

    @Test
    void when_no_inventory_and_no_money_inserted_display_soldout() {

        vendingMachine.pressButton(Product.CHIPS)

        assertEquals('SOLD OUT', vendingMachine.getDisplay())
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
    }
}
