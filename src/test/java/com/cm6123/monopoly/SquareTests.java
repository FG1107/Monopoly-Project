package com.cm6123.monopoly;

import com.cm6123.monopoly.game.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTests {

    @Test
    public void typeIsReturnedForRoadSquare(){
        // Given
        Square roadSquare = new Square(0, "Road");

        // When
        String type = roadSquare.getSquareType();

        //Then
        assertSame("Road", type);
    }

    @Test
    public void locationIsReturned(){
        // Given
        Square roadSquare = new Square(4, "Road");

        // When
        Integer location = roadSquare.getSquareLocation();

        // Then
        assertEquals(4, (int) location);
    }

    @Test
    public void defaultValuesAreRoadAnd0(){
        // Given
        Square roadSquare = new Square();

        // When
        Integer location = roadSquare.getSquareLocation();
        String type = roadSquare.getSquareType();

        // Then
        assertEquals(0, (int) location);
        assertSame("Road", type);
    }

    @Test
    public void returnOwnerWhenFunctionIsCalled(){
        // Given
        Board gameBoard = new Board(20);
        Property property = new Property(1, "tester",200.0, 20.0);
        Player tester = new Player("Test", 1);

        // When
        gameBoard.propertyPurchase(property, tester, new Rules(20));

        // Then
        assertEquals(1, property.getPropertyOwner());
    }
}
