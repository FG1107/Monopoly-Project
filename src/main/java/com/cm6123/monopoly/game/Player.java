package com.cm6123.monopoly.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    /**
     * Holds the name of the player.
     */
    private final String playerName;

    /**
     * Holds the player number.
     */
    private final Integer playerNumber;

    /**
     * Tracks the square the player is on.
     */
    private Integer playerPosition;

    /**
     * Tracks the amount of money a player has.
     */

    private Double playerBalance;

    /**
     * Holds the player's most recent roll.
     */
    private Integer recentRoll;
    /**
     * Tracks whether the player has rolled a double.
     */
    private Boolean rolledDouble;
    /**
     * List of properties owned by the user.
     */
    private final List<Square> properties = new ArrayList<>();

    /**
     * Constructor to set player name upon instance creation.
     * @param name - Name of player.
     * @param number - Player number.
     */
    public Player(final String name, final Integer number){
        this.playerName = name;
        this.playerNumber = number;
        this.playerPosition = 1;
        this.playerBalance = (double) 0;
    }

    /**
     * Get the name of the player.
     * @return string of player.
     */
    public String getPlayerName(){
        return this.playerName;
    }

    /**
     * Get the player number.
     * @return Integer representing player number.
     */
    public Integer getPlayerNumber(){
        return this.playerNumber;
    }

    /**
     * Return player's current position on the board.
     * @return player's current coordinate integer value.
     */
    public Integer getPlayerPosition(){
        return this.playerPosition;
    }

    /**
     * Returns the player's most recent roll.
     * @return - Integer value representing newest roll.
     */
    public Integer getRecentRoll(){
        return this.recentRoll;
    }

    /**
     * Updates the player's roll status.
     * @return - Boolean value of whether the player rolled a double.
     */
    public Boolean getRolledDouble(){
        return this.rolledDouble;
    }
    /**
     * Returns the value of all the player's properties combined.
     * @return Value of all purchases.
     */
    public Double calculatePropertiesValue(){
        Double totalValue = (double) 0;
        for (Square property : properties){
            totalValue += property.getPropertyPurchasePrice();
        }
        return totalValue;
    }
    /**
     * Updates the player's position on the board after a roll.
     * Keeps track of whether GO has been passed and updates balance accordingly.
     * @param board - Board being used.
     * @param rollSize - Size of the roll.
     * @param isDouble - Whether the player rolled a double.
     */
    public void updatePlayerPosition(final Board board, final Integer rollSize, final Boolean isDouble){
        Double assetValue = calculatePropertiesValue();
        this.rolledDouble = isDouble;
        this.recentRoll = rollSize;
        this.playerPosition += rollSize;
        while(this.playerPosition > board.size()){
            this.playerPosition -= board.size();
            updateBalance(200 + (0.01 * assetValue));
            System.out.println("You passed GO and collected £"+(200+(0.01*assetValue)));
        }
    }

    /**
     * Returns the player's current balance.
     * @return player's balance as Integer.
     */
    public Double returnBalance(){
        return this.playerBalance;
    }

    /**
     * Update player balance.
     * @param incoming - Value being added/subtracted to balance.
     */
    public void updateBalance(final Double incoming){
        this.playerBalance += incoming;
    }

    /**
     * Add property to owner's list of properties.
     * @param property - Property being added.
     */
    public void addProperty(final Square property){
        this.properties.add(property);
    }

    /**
     * Removes property from player's assets.
     * @param property - Property being removed.
     */
    public void removeProperty(final Square property){
        this.properties.remove(property);
    }
    /**
     * Returns the list of currently owned properties.
     * @return - All of player's properties.
     */
    public List<Square> viewProperties(){
        return this.properties;
    }
}
