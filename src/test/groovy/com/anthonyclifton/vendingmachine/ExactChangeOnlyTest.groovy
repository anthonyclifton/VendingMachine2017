package com.anthonyclifton.vendingmachine

import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals

/**
 * As a customer I want to be told when exact change is required so that I can determine
 * if I can buy something with the money I have before inserting it
 *
 * When the machine is not able to make change with the money in the machine for any of the items that it sells,
 * it will display EXACT CHANGE ONLY instead of INSERT COIN.
 */

class ExactChangeOnlyTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()
        vendingMachine.inventory = [(Product.CHIPS): 1,
                                    (Product.COLA): 1,
                                    (Product.CANDY): 1]

        vendingMachine.vault = [(Coin.QUARTER): 0,
                                (Coin.DIME): 0,
                                (Coin.NICKEL): 0]
    }

    @Test
    void display_exact_change_only_when_nothing_in_coin_vault() {
        assertEquals('EXACT CHANGE ONLY', vendingMachine.getDisplay())
    }

    @Test
    void display_exact_change_when_vault_contents_are_inadequate() {
        vendingMachine.vault = [(Coin.QUARTER): 0,
                                (Coin.DIME): 1,
                                (Coin.NICKEL): 1]
        assertEquals('EXACT CHANGE ONLY', vendingMachine.getDisplay())
    }

    @Test
    void display_insert_coin_when_vault_contents_are_adequate() {
        vendingMachine.vault = [(Coin.QUARTER): 0,
                                (Coin.DIME): 2,
                                (Coin.NICKEL): 1]
        assertEquals('INSERT COIN', vendingMachine.getDisplay())
    }
}
