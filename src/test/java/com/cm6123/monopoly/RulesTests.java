package com.cm6123.monopoly;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.Board;
import com.cm6123.monopoly.game.Rules;
import com.cm6123.monopoly.game.Square;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RulesTests {

    @Test
    public void inputsCorrectlyStored(){
        // Given
        Rules rules = new Rules(5, 10, 16);

        // When
        Integer playerCount = rules.getPlayerCount();
        Integer roundCount = rules.getRoundsLeft();
        Integer boardSize = rules.getBoardSize();

        // Then
        assertEquals(5, (int) playerCount);
        assertEquals(10, (int) roundCount);
        assertEquals(16, (int) boardSize);
    }

    @Test
    public void boardSizeTenGeneratedSuccessfully(){
        // Given
        Rules rules = new Rules( 34);

        // When
        Board gameBoard = rules.createBoard();
        List<? extends Square> squares = gameBoard.readAllSquares();

        // Then
        assertEquals(34, (int) gameBoard.size());
        assertEquals(34, squares.size());

        for (Square square : squares){
            assertNotNull(square.getSquareType());
            assertNotNull(square.getSquareLocation());
        }
    }

    @Test
    public void when2PlayersAreCreatedPlayerSizeIs2(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);

        // When
        Integer size = rules.returnPlayers().size();
        System.out.println(size);
        // Then
        assertEquals(2, (int) size);
    }

    @Test
    public void thereAre2WinnersWhen2PlayersHave1000(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);

        // When
        List<Integer> numberOfWinners = rules.declareWinner();

        // Then
        assertEquals(2, numberOfWinners.size());
    }

    @Test
    public void thereAre6WinnersWhen6PeopleHave2000(){
        // Given
        Rules rules = new Rules(8, 3, 10);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);
        rules.createPlayer("Test3", 3);
        rules.createPlayer("Test4", 4);
        rules.createPlayer("Test5", 5);
        rules.createPlayer("Test6", 6);

        // When
        List<Integer> numberOfWinners = rules.declareWinner();

        // Then
        assertEquals(6, numberOfWinners.size());
    }

    @Test
    public void playerShouldMoveAfterTurnIsTaken(){
        // Given
        Rules rules = new Rules(2, 3, 20);
        Board gameBoard = rules.createBoard();
        rules.createPlayer("Test", 1);

        // When
        Integer previousPosition = rules.returnPlayers().get(0).getPlayerPosition();
        rules.takeTurn(gameBoard, 1);
        Integer afterPosition = rules.returnPlayers().get(0).getPlayerPosition();

        // Then
        assertNotSame(previousPosition, afterPosition);
    }

    @Test
    public void currentBalanceShouldBeReturnedAs1000(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test", 1);

        // When
        Double balance = rules.returnPlayers().get(0).returnBalance();

        // Then
        assertEquals(1000.0, balance);
    }

    @Test
    public void whenA2And2AreRolledItIsKnownToBeDouble(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        Board gameBoard = rules.createBoard();
        rules.createPlayer("Test", 1);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(2,2);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        Integer roll = diceSet.sum();
        rules.takeTurn(gameBoard, 1);
    }

    @Test
    public void playerRemovedFromGame(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test", 1);

        // When
        rules.removePlayer(1);

        // Then
        assertEquals(0, rules.returnPlayers().size());
    }

    @Test
    public void playerWithBalanceOf0RegistersAsBankrupt(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test", 1);

        // When
        rules.returnPlayers().get(0).updateBalance(-1000.0);
        Boolean isBankrupt = rules.checkBankruptcy(1);

        // Then
        assertTrue(isBankrupt);
    }

    @Test
    public void playerWithBalanceOf1000RegistersAsNotBankrupt(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test", 1);

        // When
        Boolean isBankrupt = rules.checkBankruptcy(1);

        // Then
        assertFalse(isBankrupt);
    }

    @Test
    public void playerWithBalanceOfNegative500RegistersAsBankrupt(){
        // Given
        Rules rules = new Rules(2, 3, 10);
        rules.createPlayer("Test", 1);

        // When
        rules.returnPlayers().get(0).updateBalance(-1500.0);
        Boolean isBankrupt = rules.checkBankruptcy(1);

        // Then
        assertTrue(isBankrupt);
    }
}
