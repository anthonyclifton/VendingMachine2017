package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * As a vendor I want a vending machine that accepts coins
 * So that I can collect money from the customer
 *
 * The vending machine will accept valid coins (nickels, dimes, and quarters) and reject invalid ones (pennies).
 * When a valid coin is inserted the amount of the coin will be added to the current amount
 * and the display will be updated.  When there are no coins inserted, the machine displays INSERT COIN.
 * Rejected coins are placed in the coin return.
 *
 * NOTE:  The temptation here will be to create Coin objects that know their value.
 * However, this is not how a real vending machine works.  Instead, it identifies coins by their weightInGrams
 * and sizeInMillimeters and then assigns a value to what was inserted. You will need to do something similar.
 * This can be simulated using strings, constants, enums, symbols, or something of that nature.
 */

class AcceptCoinsTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()
    }

    @Test
    void valid_quarter_coin_added_to_currentAmount() {
        vendingMachine.insertCoin(Coin.QUARTER)

        assert 0.25 == vendingMachine.currentAmount
    }

    @Test
    void valid_dime_coin_added_to_currentAmount() {
        vendingMachine.insertCoin(Coin.DIME)

        assert 0.10 == vendingMachine.currentAmount
    }

    @Test
    void valid_nickel_coin_added_to_currentAmount() {
        vendingMachine.insertCoin(Coin.NICKEL)

        assert 0.05 == vendingMachine.currentAmount
    }

    @Test
    void valid_multiple_coins_added_to_currentAmount() {
        vendingMachine.insertCoin(Coin.NICKEL)
        vendingMachine.insertCoin(Coin.DIME)
        vendingMachine.insertCoin(Coin.QUARTER)

        assert 0.40 == vendingMachine.currentAmount
    }


    @Test
    void invalid_penny_coin_rejected_not_added_to_currentAmount() {
        vendingMachine.insertCoin(Coin.PENNY)

        assert 0.0 == vendingMachine.currentAmount
    }

    @Test
    void display_displays_currentAmount() {
        vendingMachine.insertCoin(Coin.QUARTER)

        assertEquals('0.25', vendingMachine.getDisplay())
    }

    @Test
    void display_insert_coin_when_currentAmount_is_zero() {
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
    }

    @Test
    void rejected_coins_are_placed_in_the_coin_return() {
        vendingMachine.insertCoin(Coin.PENNY)
        vendingMachine.insertCoin(Coin.PENNY)

        assert 2 == vendingMachine.coinReturn.findAll { it == Coin.PENNY }.size()
    }
}
