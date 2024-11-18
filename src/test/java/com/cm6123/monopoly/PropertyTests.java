package com.cm6123.monopoly;

import com.cm6123.monopoly.game.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyTests {
    @Test
    public void propertyMaintainsSquareParameters(){
        // Given
        Property mayfair = new Property();

        // When
        String type = mayfair.getSquareType();
        Integer location = mayfair.getSquareLocation();

        // Then
        assertSame("Property", type);
        assertEquals(0, (int) location);
    }

    @Test
    public void locationIsUpdatedWhenValueIsSetAs5(){
        // Given
        Property mayfair = new Property(5, "Test", 250.0, 13.0);

        // When
        Integer location = mayfair.getSquareLocation();

        // Then
        assertEquals(5, (int) location);
    }
}
