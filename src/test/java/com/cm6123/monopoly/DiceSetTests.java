package com.cm6123.monopoly;
import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiceSetTests {

    @Test
    public void shouldGet2ValuesWhenDiceSetIsRolled(){
        // Given
        DiceSet diceSet = new DiceSet(new Dice(6));

        // When
        diceSet.completeRoll();

        // Then
        assertEquals(2, (int) diceSet.size());
        assertEquals(2, diceSet.asList().size());

    }

    @Test
    public void shouldGetSumOfRolls(){
        DiceSet diceSet = new DiceSet(new Dice(6));
        diceSet.completeRoll();

        assertSame(diceSet
                .asList()
                .stream()
                .reduce(0, Integer::sum), diceSet.sum());
    }

    @Test
    public void shouldGetTwelveFromTwoSixes(){
        // Given
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(6, 6);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();

        // Then
        assertEquals(12, diceSet.sum());
    }

    @Test
    public void shouldGetElevenFromFiveAndSix(){
        // Given
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5, 6);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();

        // Then
        assertEquals(11, diceSet.sum());
    }

    @Test
    public void trueReturnedWhenDoubleRolled(){
        // Given
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5, 5);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();

        // Then
        assertTrue(diceSet.checkDouble());
    }

    @Test
    public void falseWhenDoubleIsNotRolled(){
        // Given
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5, 6);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();

        // Then
        assertFalse(diceSet.checkDouble());
    }

}
