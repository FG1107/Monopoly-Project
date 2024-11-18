package com.cm6123.monopoly;

import com.cm6123.monopoly.game.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardTests {

    @Test
    public void propertyInstanceCreatedSuccessfully(){
        // Given
        Board gameBoard = new Board(1);
        gameBoard.createSquare(1, "Property");

        // When
        List<? extends Square> squares = gameBoard.readAllSquares();
        Square firstSquare = squares.get(0);

        // Then
        assertSame(firstSquare.getSquareType(), "Property");
    }
    @Test
    public void squareIsAddedToListUponCreation(){
        // Given
        Board gameBoard = new Board(2);
        gameBoard.createSquare(1, "Road");
        gameBoard.createSquare(2, "Property");

        // When
        List<? extends Square> squares = gameBoard.readAllSquares();
        Square firstSquare = squares.get(0);
        Square secondSquare = squares.get(1);

        // Then
        assertEquals(firstSquare.getSquareLocation(), 1);
        assertSame(firstSquare.getSquareType(), "Road");

        assertSame(secondSquare.getSquareType(), "Property");
        assertEquals(secondSquare.getSquareLocation(), 2);
    }

    @Test
    public void boardIsFilledCompletelyWhenFilled(){
        // Given
        Board gameBoard = new Board(20);

        // When
        gameBoard.fillBoard();

        // Then
        assertEquals(20, gameBoard.size());
    }
    @Test
    public void twelveIsReturnedWhenBoardSizeIsTwelve(){
        // Given
        Board gameBoard = new Board(12);

        // When
        gameBoard.fillBoard();
        Integer size = gameBoard.size();

        // Then
        assertEquals(size, 12);
    }

    @Test
    public void defaultBoardSizeIsSixteen(){
        // Given
        Board gameBoard = new Board();

        // When
        gameBoard.fillBoard();
        Integer size = gameBoard.size();

        // Then
        assertEquals(size, 16);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 10, 10, 10, 20, 30, 40, 50})
    public void everySquareTypeAppearsAtLeastOnce(Integer size){
        Set<String> values = new HashSet<>();

        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        for(Square square : gameBoard.readAllSquares()){
            values.add(square.getSquareType());
        }

        assertEquals(values.size(), 4);
    }

    @Test
    public void alwaysGivesRoad(){
        // Given
        Board gameBoard = mock(Board.class);

        // When
        when(gameBoard.createRandomSquare()).thenReturn("Road","Road","Road","Road","Road","Road","Road","Road","Road","Road","Road","Road");

        // Then
        gameBoard.fillBoard();
        for (int i = 1; i < gameBoard.size(); i++){
            assertSame(gameBoard.readSquareType(i), "Road");
        }
    }

    @Test
    public void alwaysGiveProperty(){
        // Given
        Board gameBoard = mock(Board.class);

        // When
        when(gameBoard.createRandomSquare()).thenReturn("Property","Property","Property","Property","Property","Property","Property","Property","Property","Property","Property","Property","Property");

        // Then
        gameBoard.fillBoard();
        for (int i = 1; i < gameBoard.size(); i++){
            assertSame(gameBoard.readSquareType(i), "Property");
        }
    }

    @Test
    public void validBoardOfSizeTwentyIsReturned(){
        // Given
        Board gameBoard = new Board(20);

        // When
        gameBoard.fillBoard();
        List<? extends Square> squares = gameBoard.readAllSquares();

        // Then
        assertEquals(20, squares.size());

        for(Square square : squares){
            assertNotNull(square.getSquareLocation());
            assertNotNull(square.getSquareType());
        }
    }

    @Test
    public void boardSizeTenHasHeightThreeAndWidthFour(){
        // Given
        Board gameBoard = new Board(10);

        // When
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // Then
        assertEquals(4, (int) gameBoard.length());
        assertEquals(3, (int) gameBoard.height());
    }

    @Test
    public void boardSizeFiftyHasHeightSixAndWidthTwentyOne(){
        // Given
        Board gameBoard = new Board(50);

        // When
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // Then
        assertEquals(21, (int) gameBoard.length());
        assertEquals(6, (int) gameBoard.height());
    }

    @Test
    public void boardSizeThirtyFiveHasHeightSevenAndWidthThirteen(){
        // Given
        Board gameBoard = new Board(35);

        // When
        gameBoard.fillBoard();
        gameBoard.createBoard();

        System.out.println(gameBoard.length());
        System.out.println(gameBoard.height());
        // Then
        assertEquals(13, (int) gameBoard.length());
        assertEquals(7, (int) gameBoard.height());
    }

    @Test
    public void invalidBoardSizeReturnsNull(){
        // Given
        Board gameBoard = new Board(132);

        // When
        gameBoard.calculateSizing();

        // Then
        assertNull(gameBoard.length());
        assertNull(gameBoard.height());
    }

    @ParameterizedTest
    @ValueSource (ints = {10, 13, 21, 22, 30, 37, 42, 45, 50})
    public void numberOfSquaresInOutputGridShouldMatchBoardSize(Integer size){
        // Given
        Board gameBoard = new Board(size);

        // When
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // Then
        Integer squareCounter = 0;
        for (List<String> row : gameBoard.returnGrid()){
            for(String cell : row){
                if(cell.contains("□") || cell.contains("P") || cell.contains("O") || cell.contains("T")){
                    squareCounter += 1;
                }
            }
        }
        assertEquals(squareCounter, size);
    }

    @ParameterizedTest
    @ValueSource (ints = {12, 14, 15, 21, 23, 29, 30, 45, 50})
    public void firstSquareShouldAlwaysBeGo(Integer sizes){
        // Given
        Board gameBoard = new Board(sizes);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        String type = gameBoard.readSquareType(1);

        // Then
        assertSame("Road", type);
    }

    @Test
    public void noTwoPropertiesAreTheSame(){
        // Given
        Board gameBoard = new Board(50);
        List<String> nameList = new ArrayList<>();

        // When
        for (int i = 1; i <=50; i++){
            gameBoard.createNewProperty(i);
        }

        // Then
        for (Square square : gameBoard.readAllSquares()){
            String squareName = square.getName();
            System.out.println(squareName);
            assertFalse(nameList.contains(squareName));
            nameList.add(squareName);
        }
    }

    @Test
    public void propertyPurchaseNeverExceeds500(){
        // Given
        Board gameBoard = new Board(50);

        // When
        for (int i = 1; i <= 50; i++){
            gameBoard.createNewProperty(i);
        }

        // Then
        for (Square square : gameBoard.readAllSquares()){
            assertTrue(square.getPropertyPurchasePrice() < 500);
        }
    }

    @Test
    public void rentNeverExceedsTenthOfPurchasePrice() {
        // Given
        Board gameBoard = new Board(50);

        // When
        for (int i = 1; i <= 50; i++) {
            gameBoard.createNewProperty(i);
        }

        // Then
        for (Square square : gameBoard.readAllSquares()) {
            assertTrue(square.getPropertyRentalPrice() <= square.getPropertyPurchasePrice() / 10);
        }
    }

    @Test
    public void playerLoses200AndGainsHouseAfterBuying200PricedProperty(){
        // Given
        Board gameBoard = new Board(50);
        Property testHouse = new Property(1, "TestHouse", 200.0, 10.0);
        Player tester = new Player("Tester", 1);

        // When
        gameBoard.propertyPurchase(testHouse, tester, new Rules(50));

        // Then
        assertEquals(tester.returnBalance(), -200);
        assertEquals(tester.viewProperties().size(), 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {10,12,14,16,18})
    public void numberOfRowsForEvenBoardOfSize10To19ShouldBe3(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(3, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {11,13,15,17,19})
    public void numberOfRowsForOddBoardOfSize10To19ShouldBe5(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(5, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {20,22,24,26,28})
    public void numberOfRowsForEvenBoardOfSize20To29ShouldBe4(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(4, rows);
    }
    @ParameterizedTest
    @ValueSource(ints = {21,23,25,27,29})
    public void numberOfRowsForOddBoardOfSize20To29ShouldBe6(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(6, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {30,32,34,36,38})
    public void numberOfRowsForEvenBoardOfSize30To39ShouldBe6(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(5, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {31,33,35,37,39})
    public void numberOfRowsForOddBoardOfSize30To39ShouldBe7(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(7, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {40,42,44,46,48,50})
    public void numberOfRowsForEvenBoardOfSize40To50ShouldBe6(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(6, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {41,43,45,47,49})
    public void numberOfRowsForOddBoardOfSize40To50ShouldBe8(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();
        gameBoard.createBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertEquals(8, rows);
    }

    @ParameterizedTest
    @ValueSource(ints = {2,7,9,55,89})
    public void numberOfRowsForUnavailableBoardSizeShouldBeNull(Integer size){
        // Given
        Board gameBoard = new Board(size);
        gameBoard.fillBoard();

        // When
        Integer rows = gameBoard.height();

        // Then
        assertNull(rows);
    }

    @Test
    public void rentIsChargedWhenPlayerLandsOnOwnedSpace(){
        // Given
        Rules rules = new Rules(20);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();
        gameBoard.createBoard();
        Property property = new Property(1, "Tester", 200.0, 20.0);
        gameBoard.createBoard();

        // When
        gameBoard.propertyPurchase(property, rules.returnPlayers().get(0), rules);
        gameBoard.propertyRent(property, rules.returnPlayers().get(1), rules);

        // Then
        assertEquals(980, rules.returnPlayers().get(1).returnBalance());
        assertEquals(820, rules.returnPlayers().get(0).returnBalance());
    }

    @Test
    public void ownedPropertyCannotBeBought(){
        // Given
        Rules rules = new Rules(20);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();
        gameBoard.createBoard();
        Property property = new Property(1, "Tester", 200.0, 20.0);
        gameBoard.createBoard();

        // When
        gameBoard.propertyPurchase(property, rules.returnPlayers().get(0), rules);

        // Then
        assertFalse(gameBoard.propertyOwned(property));
    }

    @Test
    public void unownedPropertyCanBeBought(){
        // Given
        Rules rules = new Rules(20);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();
        gameBoard.createBoard();
        Property property = new Property(1, "Tester", 200.0, 20.0);
        gameBoard.createBoard();

        // When nothing is done

        // Then
        assertTrue(gameBoard.propertyOwned(property));
    }

    @Test
    public void playerCanSellProperty(){
        // Given
        Rules rules = new Rules(20);
        rules.createPlayer("Test1", 1);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();
        gameBoard.createBoard();
        Property property = new Property(1, "Tester", 200.0, 20.0);

        // When
        gameBoard.propertyPurchase(property, rules.returnPlayers().get(0), rules);
        gameBoard.propertySell(rules.returnPlayers().get(0), property);

        // Then
        assertEquals(1000.0, rules.returnPlayers().get(0).returnBalance());
        assertEquals(0, rules.returnPlayers().get(0).calculatePropertiesValue());
    }

    @Test
    public void playerRegistersAsBankruptAfterLandingOnRent(){
        // Given
        Rules rules = new Rules(20);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();
        gameBoard.createBoard();
        Property property = new Property(1, "Tester", 200.0, 20.0);
        rules.returnPlayers().get(1).updateBalance(-990.0);

        // When
        gameBoard.propertyPurchase(property, rules.returnPlayers().get(0), rules);
        gameBoard.propertyRent(property, rules.returnPlayers().get(1), rules);

        // Then
        assertTrue(rules.checkBankruptcy(2));
    }

    @Test
    public void playerIsRegisteredAsBankruptAfterLandingOnTrain(){
        // Given
        Rules rules = new Rules(20);
        rules.createPlayer("Test1", 1);
        rules.createPlayer("Test2", 2);
        Board gameBoard = new Board(20);
        gameBoard.fillBoard();
        gameBoard.createBoard();
        TrainStation station = new TrainStation(1, "Tester", 200.0);
        rules.returnPlayers().get(1).updateBalance(-990.0);

        // When
        station.returnStationPayment(10, rules.returnPlayers().get(1));

        // Then
        assertTrue(rules.checkBankruptcy(2));
    }
}
