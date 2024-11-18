package com.cm6123.monopoly;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTests {
    @Test
    public void playerCreatedSuccessfully(){
        // Given
        Player tester = new Player("Test", 1);

        // When
        String name = tester.getPlayerName();
        Integer id = tester.getPlayerNumber();

        // Then
        assertEquals("Test", name);
        assertEquals(1, (int) id);
    }

    @Test
    public void playerLocationShouldBe6AfterRollingA5OnSquare1(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        Board gameBoard = rules.createBoard();
        Player test = new Player("Tester", 1);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(2,3);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        Integer roll = diceSet.sum();
        test.updatePlayerPosition(gameBoard, roll, diceSet.checkDouble());

        // Then
        assertEquals(6, test.getPlayerPosition());
    }

    @Test
    public void playerPositionShouldBe1AfterRolling10OnBoardSize10(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        Board gameBoard = rules.createBoard();
        Player test = new Player("Tester", 1);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5,5);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        Integer roll = diceSet.sum();
        test.updatePlayerPosition(gameBoard, roll, diceSet.checkDouble());

        // Then
        assertEquals(1, test.getPlayerPosition());
    }

    @Test
    public void balanceShouldBeUpdatedBy350(){
        // Given
        Player test = new Player("Test", 1);

        // When
        test.updateBalance(350.0);

        // Then
        assertEquals(350.0, test.returnBalance());
    }

    @Test
    public void purchasedPropertiesAddedToAssets(){
        // Given
        Player tester = new Player("Test", 1);
        Property property = new Property(1, "Property", 20.0, 5.0);

        // When
        tester.addProperty(property);

        // Then
        List<Square> testerProperties = tester.viewProperties();
        assertEquals(testerProperties.size(), 1);
        assertSame(testerProperties.get(0), property);
    }

    @Test
    public void ifAPlayerPassesGoTwiceTheyReceiveMoneyTwice(){
        // Given
        Player tester = new Player("Test1", 1);
        Board gameBoard = new Board(10);
        gameBoard.fillBoard();

        // When
        tester.updatePlayerPosition(gameBoard, 21, false);

        // Then
        assertEquals(tester.returnBalance(), 400.0);
    }

    @Test
    public void recentRollReturned(){
        // Given
        Player tester = new Player("Test", 1);
        Board gameBoard = new Board(10);
        gameBoard.fillBoard();
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(2,3);
        DiceSet diceset = new DiceSet(rollRange);

        // When
        diceset.completeRoll();
        tester.updatePlayerPosition(gameBoard, diceset.sum(), diceset.checkDouble());

        // Then
        assertEquals(5, tester.getRecentRoll());
    }

    @Test
    public void whenPropertyOfValue300And250ArePurchasedTotalValueIs550(){
        // Given
        Player tester = new Player("Test", 1);
        Property property1 = new Property(1, "Property1", 300.0, 5.0);
        Property property2 = new Property(2, "Property2", 250.0, 5.0);

        // When
        tester.addProperty(property1);
        tester.addProperty(property2);

        // Then
        assertEquals(550.0, tester.calculatePropertiesValue());
    }

    @Test
    public void propertyRemovedWhenDeletedFromPlayerList(){
        // Given
        Player tester = new Player("Test", 1);
        Property property = new Property(1, "Property", 20.0, 5.0);
        tester.addProperty(property);

        // When
        tester.removeProperty(property);

        // Then
        List<Square> testerProperties = tester.viewProperties();
        assertEquals(testerProperties.size(), 0);
    }

    @Test
    public void isDoubleIsTrueWhenPlayerRolls5and5(){
        // Given
        Player tester = new Player("Test", 1);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5,5);
        DiceSet diceset = new DiceSet(rollRange);

        // When
        diceset.completeRoll();
        Boolean isDouble = diceset.checkDouble();

        // Then
        assertTrue(isDouble);
    }

    @Test
    public void isDoubleIsFalseWhenPlayerRolls5and4(){
        // Given
        Player tester = new Player("Test", 1);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5,4);
        DiceSet diceset = new DiceSet(rollRange);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();

        // When
        diceset.completeRoll();
        tester.updatePlayerPosition(gameBoard, diceset.sum(), diceset.checkDouble());
        Boolean isDouble = tester.getRolledDouble();

        // Then
        assertFalse(isDouble);
    }
}
