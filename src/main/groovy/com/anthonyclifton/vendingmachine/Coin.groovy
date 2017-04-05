package com.anthonyclifton.vendingmachine

/**
 * This is a convenience so we can say we're inserting or storing a certain kind of
 * coin.  However, only the size and weight will be gettable here.  Coins have a
 * value embossed on them but vending machines don't read that.  The vending machine
 * will use the size and weight to determine the correct vault slot into which the
 * coin will be sorted and that slot will have a value assigned to it.
 */

enum Coin {
    DIME(1.0,2.0),
    QUARTER(3.0,4.0),
    NICKEL(5.0, 6.0),
    PENNY(7.0, 8.0)

    float size
    float weight

    Coin(float size, float weight) {
        this.size = size
        this.weight = weight
    }
}
