package com.cm6123.monopoly.game;

public class Square {

    /**
     * Keeps track of the square's location on the board.
     */
    private Integer squareLocation;

    /**
     * Keeps track of whether the square is a road, property, etc.
     */
    private String squareType;

    /**
     * Stores how the square will be represented on the gameboard.
     */
    private String squareRep;

    /**
     * Name used to refer to the property.
     */
    private String propertyName;

    /**
     * Current ID of the owner of the property. Can be null.
     */
    private Integer propertyOwner;

    /**
     * Price of purchasing the property.
     */
    private Double propertyPurchasePrice;

    /**
     * Price of renting the property.
     */
    private Double propertyRentalPrice;

    /**
     * Cost of landing on train station.
     */
    private Double stationCost;

    /**
     * Cost of landing on tax office.
     */
    private Double officeCost;
    /**
     * Default constructor to set Road as default square.
     */
    public Square() {
        this.squareType = "Road";
        this.squareLocation = 0;
        this.squareRep = " □ ";
    }

    /**
     * Construct a square with the given parameters.
     * @param boardCoordinate
     * @param type
     */
    public Square(final Integer boardCoordinate, final String type){
        this.squareLocation = boardCoordinate;
        this.squareType = type;
        this.squareRep = " □ ";
    }

    /**
     * Used to return the type of square.
     * @return whether the square is a road, property, etc.
     */
    public String getSquareType(){
        return this.squareType;
    }

    /**
     * Updates the square type of any children.
     * @param type - New square type.
     * @param representor - New square representor for the grid,
     */
    public void updateSquareType(final String type, final String representor){
        this.squareType = type;
        this.squareRep = representor;
    }
    /**
     * Used to return the location of the square.
     * @return an integer representing the square position.
     */
    public Integer getSquareLocation() {
        return this.squareLocation;
    }

    /**
     * Updates board location of a child.
     * @param location - Location on the board.
     */
    public void updateLocation(final Integer location){
        this.squareLocation = location;
    }
    /**
     * Used to return the character representing the square type on the board.
     * @return string of square rep.
     */
    public String getSquareRepresentor(){
        return this.squareRep;
    }


    /**
     * Returns the name of the instance of the property.
     * @return name of the property as string.
     */
    public String getName(){
        return this.propertyName;
    }

    /**
     * Updates the name of a property if needed.
     * @param name - New name of property.
     */
    public void updateName(final String name){
        this.propertyName = name;
    }
    /**
     * Returns the ID of the owner of the property.
     * @return ID of property owner as integer.
     */
    public Integer getPropertyOwner(){
        return this.propertyOwner;
    }

    /**
     * Returns the price of purchasing the property.
     * @return price of property purchase as integer.
     */
    public Double getPropertyPurchasePrice(){
        return this.propertyPurchasePrice;
    }

    /**
     * Returns the price of renting the property.
     *
     * @return price of renting the property.
     */
    public Double getPropertyRentalPrice(){
        return this.propertyRentalPrice;
    }

    /**
     * Updates the prices of a child.
     * @param purchase - New purchase price.
     * @param rent - New rental price.
     */
    public void updatePrices(final Double purchase, final Double rent){
        this.propertyPurchasePrice = purchase;
        this.propertyRentalPrice = rent;
    }

    /**
     * Updates the cost of a train station.
     * @param price - Price of station.
     */
    public void updateStationCost(final Double price){
        this.stationCost = price;
    }
    /**
     * Changes the owner of the property.
     * @param newOwner - ID of the new owner.
     */
    public void updateOwner(final Integer newOwner){
        this.propertyOwner = newOwner;
    }

    /**
     * Returns the price of landing on a station.
     * @return integer representing price.
     */
    public Double returnStationCost(){
        return this.stationCost;
    }

    /**
     * Calculates the price of landing on the train station.
     * @param roll - Sum of roll of player who landed on the station.
     * @param player - Player who rolled onto the station.
     * @return payment that is due.
     */
    public Double returnStationPayment(final Integer roll, final Player player){
        System.out.println("You have been charged £"+(this.stationCost * roll));
        player.updateBalance(-(this.stationCost * roll));
        return this.stationCost * roll;
    }

    /**
     * Returns the cost of landing on the tax office.
     * @return - Double value of landing on the office.
     */
    public Double returnOfficeCost(){
        return this.officeCost;
    }

    /**
     * Updates the cost of the tax office.
     * @param cost - New cost of landing on the office.
     */
    public void updateOfficeCost(final Double cost){
        this.officeCost = cost;
    }

    /**
     * Calculates the cost of landing on the tax office.
     * @param player - Player who landed on the office.
     * @param rollSum - Sum of the roll of the player.
     * @param isDouble - Whether the player rolled a double.
     */
    public void returnOfficePayment(final Player player, final Integer rollSum, final Boolean isDouble){
        System.out.println("You landed on a tax office... You owe "+rollSum+"% of your current balance.");
        if(isDouble){
            System.out.println("You rolled a double! Your tax is halved.");
            System.out.println("You have been charged £"+(((0.01 * rollSum)/2) * player.returnBalance()));
            player.updateBalance(-(((0.01 * rollSum)/2) * player.returnBalance()));
        } else{
            System.out.println("You have been charged £"+((0.01 * rollSum) * player.returnBalance()));
            player.updateBalance(-((0.01 * rollSum) * player.returnBalance()));
        }
    }
}
