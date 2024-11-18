package com.cm6123.monopoly.game;

public class TrainStation extends Square{

    /**
     * Default constructor to ensure the details for the
     * square are correct.
     */
    public TrainStation(){
        updateSquareType("Train Station", " T ");
    }

    /**
     * Assign values on instance creation.
     * @param location - Coordinate on board.
     * @param name - Name of train station.
     * @param unitPrice - Base price of landing on station.
     */
    public TrainStation(final Integer location, final String name, final Double unitPrice){
        updateSquareType("Train Station", " T ");
        updateStationCost(unitPrice);
        updateLocation(location);
        updateName(name);
    }
}
