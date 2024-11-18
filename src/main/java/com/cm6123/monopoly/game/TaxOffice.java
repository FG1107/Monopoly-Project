package com.cm6123.monopoly.game;

public class TaxOffice extends Square {
    /**
     * Default constructor to set the squareType as Tax Office.
     */
    public TaxOffice(){
        updateSquareType("Tax Office", " O ");
    }

    /**
     * Constructor to add a location value.
     * @param location - Integer value of grid location for square.
     * @param unitPrice - Integer value of property purchase price.
     */
    public TaxOffice(final Integer location, final Double unitPrice) {
        updateSquareType("Tax Office", " O ");
        updateOfficeCost(unitPrice);
        updateLocation(location);
    }
}
