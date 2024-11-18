package com.cm6123.monopoly;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.Player;
import com.cm6123.monopoly.game.TrainStation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StationTests {
    @Test
    public void stationCostIsReturned(){
        // Given
        TrainStation station = new TrainStation(1, "Test", 250.0);

        // When
        Double cost = station.returnStationCost();

        // Then
        assertEquals(cost, 250);
    }

    @Test
    public void playerIsCharged250AfterRollingA5OnAStationWorth50(){
        // Given
        Player tester = new Player("Tester", 1);
        TrainStation station = new TrainStation(1, "TestStation", 50.0);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(2, 3);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        station.returnStationPayment(diceSet.sum(), tester);

        // Then
        assertEquals(tester.returnBalance(), -250);
    }
    @Test
    public void playerIsCharged500AfterRollingA5OnAStationWorth100(){
        // Given
        Player tester = new Player("Tester", 1);
        TrainStation station = new TrainStation(1, "TestStation", 100.0);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(2, 3);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        station.returnStationPayment(diceSet.sum(), tester);

        // Then
        assertEquals(tester.returnBalance(), -500);
    }

    @Test
    public void defaultStationCreatedSuccessfully(){
        // Given
        TrainStation station = new TrainStation();

        // When
        String type = station.getSquareType();
        String rep = station.getSquareRepresentor();

        // Then
        assertEquals(type, "Train Station");
        assertEquals(rep, " T ");
    }
}
