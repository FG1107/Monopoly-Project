package com.cm6123.monopoly;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaxOfficeTests {
    @Test
    public void officeCostIsReturned(){
        // Given
        TaxOffice office = new TaxOffice(1, 250.0);

        // When
        Double cost = office.returnOfficeCost();

        // Then
        assertEquals(cost, 250);
    }

    @Test
    public void typeSetToTaxOffice(){
        // Given
        TaxOffice office = new TaxOffice(1, 250.0);

        // When
        String type = office.getSquareType();

        // Then
        assertEquals(type, "Tax Office");
    }

    @Test
    public void defaultOfficeHasCorrectValues(){
        // Given
        TaxOffice office = new TaxOffice();

        // When
        String type = office.getSquareType();
        String representation = office.getSquareRepresentor();
        Double cost = office.returnOfficeCost();
        Integer location = office.getSquareLocation();

        // Then
        assertEquals(representation, " O ");
        assertEquals(type, "Tax Office");
        assertNull(cost);
        assertEquals(location, 0);
    }

    @Test
    public void rolling5And6Charges11PercentOfCurrentBalance(){
        // Given
        Rules rules = new Rules(10);
        Board gameBoard = new Board(50);
        gameBoard.fillBoard();
        TaxOffice office = new TaxOffice(1, 100.0);
        rules.createPlayer("Tester", 1);
        Player player = rules.returnPlayers().get(0);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(5, 6);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        player.updatePlayerPosition(gameBoard, diceSet.sum(), diceSet.checkDouble());
        office.returnOfficePayment(player, player.getRecentRoll(), player.getRolledDouble());

        // Then
        assertEquals(890.0, player.returnBalance());
    }

    @Test
    public void rolling6And6Charges6PercentOfCurrentBalance(){
        // Given
        Rules rules = new Rules(10);
        Board gameBoard = new Board(50);
        gameBoard.fillBoard();
        TaxOffice office = new TaxOffice(1, 100.0);
        rules.createPlayer("Tester", 1);
        Player player = rules.returnPlayers().get(0);
        Dice rollRange = mock(Dice.class);
        when(rollRange.roll()).thenReturn(6, 6);
        DiceSet diceSet = new DiceSet(rollRange);

        // When
        diceSet.completeRoll();
        player.updatePlayerPosition(gameBoard, diceSet.sum(), diceSet.checkDouble());
        office.returnOfficePayment(player, player.getRecentRoll(), player.getRolledDouble());

        // Then
        assertEquals(940.0, player.returnBalance());
    }
}
