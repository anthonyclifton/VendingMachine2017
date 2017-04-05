package com.anthonyclifton.vendingmachine

/**
 * This is a convenience so we can say we're inserting or storing a certain kind of
 * coin.  However, only the sizeInMillimeters and weightInGrams will be gettable here.  Coins have a
 * value embossed on them but vending machines don't read that.  The vending machine
 * will use the sizeInMillimeters and weightInGrams to determine the correct vault slot into which the
 * coin will be sorted and that slot will have a value assigned to it.
 */

/**
 * Sizes and Weights from https://en.wikipedia.org/wiki/United_States_Mint_coin_sizes
 * Small Cent 19.05mm 2.5g 1857–present
 * Nickel 21.21mm 5g 1866–present
 * Quarter 24.26mm 5.67g 1965–present
 * Dime 17.91mm 2.268g 1965–present
 */

enum Coin {
    DIME(17.91, 2.268),
    QUARTER(24.26, 5.67),
    NICKEL(21.21, 5.0),
    PENNY(19.05, 2.5)

    float sizeInMillimeters
    float weightInGrams

    Coin(float sizeInMillimeters, float weightInGrams) {
        this.sizeInMillimeters = sizeInMillimeters
        this.weightInGrams = weightInGrams
    }
}
