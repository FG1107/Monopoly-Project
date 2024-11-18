package com.cm6123.monopoly.game;

public class Property extends Square{
    /**
     * Default constructor to set the squareType as property.
     */
    public Property(){
        updateSquareType("Property", " P ");
    }

    /**
     * Constructor to add a location value.
     * @param location - Integer value of grid location for square.
     * @param name - String value of name of property.
     * @param purchasePrice - Integer value of property purchase price.
     * @param rentalPrice - Integer value of property rental price.
     */
    public Property(final Integer location, final String name, final Double purchasePrice, final Double rentalPrice) {
        updateSquareType("Property", " P ");
        updatePrices(purchasePrice, rentalPrice);
        updateLocation(location);
        updateName(name);
    }
}
