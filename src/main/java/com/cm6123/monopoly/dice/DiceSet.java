package com.cm6123.monopoly.dice;

import java.util.ArrayList;
import java.util.List;

public class DiceSet {
    /**
     * numberOfDice - The number of dice in the set.
     */
    private final Integer numberOfDice = 2;
    /**
     * dice - Used to store an instance of the dice for re-rolls.
     */
    private final Dice dice;
    /**
     * rollsMade - Used to store what numbers are rolled by the dice.
     */
    private final List<Integer> rollsMade;

    /**
     * Constructor to create a new Dice class for each set.
     * @param inputDice - Dice being used.
     */
    public DiceSet(final Dice inputDice){
        this.dice = inputDice;
        rollsMade = new ArrayList<>();
    }

    /**
     * Roll the dice.
     */
    public final void completeRoll(){
        for (int i = 0; i < numberOfDice; i ++){
            rollsMade.add(dice.roll());
        }
    }

    /**
     * Check if both die have the same output.
     * @return boolean of true or false.
     */
    public Boolean checkDouble(){
        if (rollsMade.get(0) == rollsMade.get(1)){
            return true;
        }
        return false;
    }

    /**
     * Retrieve list of rolls made by the set - for use of tracking doubles.
     * @return a list of integers representing recent dice rolls.
     */
    public final List<Integer> asList(){
        return List.copyOf(rollsMade);
    }

    /**
     * Calculates the sum of recent rolls.
     * @return Sum of recent rolls, between 2 and 12.
     */
    public final Integer sum(){
        Integer sum = 0;
        for (Integer roll : rollsMade){
            sum += roll;
        }
        return sum;
    }

    /**
     * Finds the size of the dice set - for testing purposes.
     * @return amount of dice being rolled.
     */
    public final Integer size(){
        return rollsMade.size();
    }
}
