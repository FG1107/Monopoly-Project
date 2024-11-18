package com.cm6123.monopoly.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Board {

    /**
     * List of possible properties that could be on the board.
     */
    private final String[] possiblePropertyNames = {
            "Park Avenue", "Broadway", "Main Street", "Elm Street", "High Street",
            "Maple Avenue", "Elmwood Drive", "Pine Street", "Oak Lane", "Washington Avenue",
            "Market Street", "River Road", "Sunset Boulevard", "Lakeview Drive", "Cherry Lane",
            "Vine Street", "Bridge Street", "Willow Way", "Hillcrest Avenue", "Cedar Street",
            "Chestnut Avenue", "Forest Drive", "Maplewood Avenue", "Pinecrest Drive", "Oakwood Road",
            "Birch Lane", "Park Place", "Boardwalk", "Pennsylvania Avenue",
            "Fifth Avenue", "Wall Street", "Madison Avenue", "Rodeo Drive", "Michigan Avenue",
            "Sunset Avenue", "Ocean Avenue", "Atlantic Avenue", "Pacific Avenue", "Beach Boulevard",
            "River Street", "Lakeside Drive", "Mountain View Road", "Valley Road", "Hillside Avenue",
            "Harbor Drive", "Bayview Avenue", "Sea Breeze Boulevard", "Sunflower Street", "Meadow Lane", "London Street",
            "Edinburgh Valley", "Newmans Way"
    };

    /**
     * List of potential train station names.
     */
    private final String[] trainStations = {
            "Grand Central Terminal",
            "Penn Station",
            "Union Station",
            "King's Cross Station",
            "Shinjuku Station",
            "Gare du Nord",
            "Central Station",
            "Tokyo Station",
            "Berlin Hauptbahnhof",
            "Amsterdam Centraal",
            "St Pancras International",
            "Hamburg Hauptbahnhof",
            "Frankfurt Hauptbahnhof",
            "Zurich Hauptbahnhof",
            "Munich Hauptbahnhof",
            "Barcelona Sants",
            "Madrid Atocha",
            "Milano Centrale",
            "Roma Termini",
            "Paris Gare de Lyon",
            "Osaka Station",
            "Kyoto Station",
            "Seoul Station",
            "Beijing Railway Station",
            "Shanghai Hongqiao Station",
            "Moscow Leningradsky Station",
            "London Waterloo",
            "New York City Subway",
            "Chicago Union Station",
            "Los Angeles Union Station",
            "Sydney Central Station",
            "Melbourne Central",
            "Toronto Union Station",
            "Vancouver Pacific Central Station",
            "Montreal Central Station",
            "Riyadh Railway Station",
            "Dubai Union Station",
            "Hong Kong West Kowloon Station",
            "Singapore Tanjong Pagar Railway Station",
            "Kuala Lumpur Sentral",
            "Bangkok Hua Lamphong Station",
            "Cairo Ramses Station",
            "Buenos Aires Retiro Station",
            "São Paulo Luz Station",
            "Mexico City Buenavista Station",
            "Moscow Kiyevsky Railway Station",
            "St. Petersburg Moscow Station",
            "Tokyo Shibuya Station"
    };

    /**
     * Creates a list of possible property names.
     */
    private final List<String> listOfPropertyNames = Arrays.asList(possiblePropertyNames);

    /**
     * Creates a list of possible station names.
     */
    private final List<String> listOfStationNames = Arrays.asList(trainStations);
    /**
     * Makes a mutable list of these street names.
     */
    private final List<String> arrayListOfStreetNames = new ArrayList<>(listOfPropertyNames);

    /**
     * Makes a mutable list of these station names.
     */
    private final List<String> arrayListOfStationNames = new ArrayList<>(listOfStationNames);
    /**
     * List of all the squares on the board.
     */
    private final List<Square> squares = new ArrayList<>();

    /**
     * Declares the size of the board.
     */
    private final Integer boardSize;

    /**
     * Keeps score of how many rows the board should output with.
     */
    private Integer boardRowCount;

    /**
     * Keeps score of how many columns the board should output.
     */
    private Integer boardColumnCount;

    /**
     * How the board will be shown to the user.
     */
    private final List<List<String>> board = new ArrayList<>();
    /**
     * Default constructor to set 16 as default board size.
     */
    public Board(){
        this.boardSize = 16;
    }

    /**
     * Constructor to create a board according to the size inputted.
     * @param size = Desired length of gameboard.
     */
    public Board(final Integer size){
        this.boardSize = size;
    }

    /**
     * Return the size of the board.
     * @return - Size of board as integer.
     */
    public Integer size(){
        return squares.size();
    }

    /**
     * Gives the length that the board will be outputted with.
     * @return integer representing length value.
     */
    public Integer length(){
        return this.boardColumnCount;
    }

    /**
     * Gives the height that the board will be outputted with.
     * @return integer representing height value.
     */
    public Integer height(){
        return this.boardRowCount;
    }

    /**
     * Returns the grid to access outside the class.
     * @return grid with square details that user sees.
     */
    public List<List<String>> returnGrid(){
        return this.board;
    }

    /**
     * Randomly generate a new property.
     * @param coord - Location of property on board.
     */
    public void createNewProperty(final Integer coord){
        Random random = new Random();
        int purchasePrice = random.nextInt(49) + 1;
        int rentalPrice = random.nextInt(purchasePrice) + 1;
        purchasePrice *= 10;
        int propertySelector = random.nextInt(arrayListOfStreetNames.size() - 1);
        String propertyName = arrayListOfStreetNames.get(propertySelector);
        arrayListOfStreetNames.remove(propertySelector);
        Property property = new Property(coord, propertyName, (double) purchasePrice, (double) rentalPrice);
        squares.add(property);
    }

    /**
     * Creates a new tax office.
     * @param coord - Location on the board.
     */
    public void createTaxOffice(final Integer coord){
        Random random = new Random();
        int taxAmount = random.nextInt(9) + 1;
        taxAmount *= 10;
        TaxOffice taxOffice = new TaxOffice(coord, (double) taxAmount);
        taxOffice.updateStationCost((double) taxAmount);
        squares.add(taxOffice);
    }
    /**
     * Creates an instance of the train station class.
     * @param coord - Location on the board.
     */
    public void createTrainStation(final Integer coord){
        Random random = new Random();
        int unitPrice = random.nextInt(9) + 1;
        unitPrice *= 10;
        int stationSelector = random.nextInt(arrayListOfStationNames.size() - 1);
        String stationName = arrayListOfStationNames.get(stationSelector);
        arrayListOfStationNames.remove(stationSelector);
        TrainStation station = new TrainStation(coord,stationName, (double) unitPrice);
        squares.add(station);
    }
    /**
     * Function to create a new square based on input parameters and
     * add it to the board.
     * @param coord - Square location on the board.
     * @param type - Whether the square is a road, property, etc.
     */
    public void createSquare(final Integer coord, final String type) {
        if (type == "Property") {
            createNewProperty(coord);
        } else if (type == "Train Station"){
            createTrainStation(coord);
        } else if(type == "Tax Office"){
            createTaxOffice(coord);
        }else{
            Square square = new Square(coord, "Road");
            squares.add(square);
        }
        squares.sort(Comparator.comparing(Square::getSquareLocation));
    }

    /**
     * Used to see the details of a specific square.
     * @param coord - Location of the square being asked about.
     * @return String of the type of square being queried.
     */
    public String readSquareType(final Integer coord){
        String details = squares.get(coord - 1).getSquareType();
        Integer location = squares.get(coord - 1).getSquareLocation();
        System.out.println("Square Location : " + location);
        System.out.println("Square Type : " + details);
        return details;
    }

    /**
     * Print every square currently stored on the board and return the list of squares.
     * @return List of all squares currently stored in the board.
     */
    public List<? extends Square> readAllSquares(){
        return this.squares;
    }

    /**
     * Generates a random square type out of the available options.
     * @return randomly selected square type as String.
     */
    public String createRandomSquare(){
        List<String> squareOptions = new ArrayList<>();
        squareOptions.add("Road");
        squareOptions.add("Property");
        squareOptions.add("Train Station");
        squareOptions.add("Tax Office");
        Random random = new Random();
        String randomSquareType = squareOptions.get(random.nextInt(squareOptions.size()));
        return randomSquareType;
    }

    /**
     * This function fills the board with a random selection of squares.
     */
    public void fillBoard(){
        String squareType;
        List<String> unusedSpaces = new ArrayList<>();
        unusedSpaces.add("Property");
        unusedSpaces.add("Train Station");
        unusedSpaces.add("Tax Office");
        calculateSizing();
        createSquare(1, "Road");
        for(int i = 2; i <= boardSize; i++){
            if (unusedSpaces.size() == 1){
                squareType = unusedSpaces.get(0);
            } else {
                squareType = createRandomSquare();
            }
            if (unusedSpaces.contains(squareType)){
                unusedSpaces.remove(squareType);
                createSquare(i, squareType);
            } else{
                createSquare(i, squareType);
            }
        }
    }

    /**
     * Calculates the width and height of the board.
     */
    public void calculateSizing(){
        Integer size = this.boardSize;
        if (size >= 10 && size < 20) {
            this.boardRowCount = 3;
        } else if (size >= 20 && size < 30){
            this.boardRowCount = 4;
        } else if (size >= 30 && size < 40){
            this.boardRowCount = 5;
        } else if (size >= 40 && size <= 50){
            this.boardRowCount = 6;
        } else{
            this.boardRowCount = null;
        }
        if((size % 2 != 0) && this.boardRowCount != null){
            this.boardRowCount += 1;
            this.boardColumnCount = ((size - 1) / 2) - this.boardRowCount + 2;
        } else if(this.boardRowCount == null){
            this.boardColumnCount = null;
        } else{
            this.boardColumnCount = (size / 2) - this.boardRowCount + 2;
        }
    }
    /**
     * Create the board that the user will see using known info.
     */
    public void createBoard(){
        List<String> oddException = new ArrayList<String>();
        Integer squareCount = 0;
        Integer exceptCounter = 0;
        // Generate a row for each in the row count.
        for (int i = 0; i < this.boardRowCount; i++) { // Error handling for grids of odd size
            if(i == 1 && this.boardSize % 2 != 0){
                for (int j = 0; j < this.boardColumnCount; j++) {
                    if(j == this.boardColumnCount - 1){
                        oddException.add(squares.get(this.boardColumnCount + i - 1).getSquareRepresentor());
                    } else{
                        oddException.add(" - ");
                    }
                }
                this.board.add(1, oddException);
                squareCount += 1;
                this.boardRowCount += 1;
                exceptCounter += 1;
            } else{
            List<String> newRow = new ArrayList<String>();
            // Code for the middle rows.
            if(i != 0 && i != this.boardRowCount - 1){
                for (int j = 0; j < this.boardColumnCount; j++) {
                    if(j == 0){
                        newRow.add(squares.get(this.boardSize - i + exceptCounter).getSquareRepresentor());
                    } else if (j == this.boardColumnCount - 1){
                        newRow.add(squares.get(this.boardColumnCount + i - 1).getSquareRepresentor());
                    } else{
                        newRow.add(" - ");
                    }
                }
            } else if (i == 0){  // Code for the first row
                for (int j = 0; j < this.boardColumnCount; j++) {
                   newRow.add(squares.get(squareCount).getSquareRepresentor());
                   squareCount ++;
                }
            } else{ // Code for the last row
                for (int j = 0; j < this.boardColumnCount; j++){
                    newRow.add(squares.get((squares.size() - this.boardRowCount - 1 - j) + 2 + exceptCounter).getSquareRepresentor());
                }
            }
            this.board.add(newRow);
        }
        }
    }
    /**
     * Registers the purchase of a property.
     * @param property - Property being purchased.
     * @param player - Player purchasing the property.
     * @param rules - Stores list of all players.
     */
    public void propertyPurchase(final Square property, final Player player, final Rules rules){
            property.updateOwner(player.getPlayerNumber());
            player.addProperty(property);
            player.updateBalance((double) -property.getPropertyPurchasePrice());
    }

    /**
     * Handles rent payments for a property.
     * @param property - Property that was landed on.
     * @param player - Player that owes money.
     * @param rules - Stores all players playing the game currently.
     */
    public void propertyRent(final Square property,final Player player, final Rules rules){
        List<Player> players = rules.returnPlayers();
        for (Player singlePlayer : players){
            if(singlePlayer.getPlayerNumber() == property.getPropertyOwner()){
                System.out.println("This property is owned by Player "+singlePlayer.getPlayerName()+".");
                singlePlayer.updateBalance(property.getPropertyRentalPrice());
                player.updateBalance(-property.getPropertyRentalPrice());
                System.out.println(player.getPlayerName()+" has sent "+singlePlayer.getPlayerName()+" £"+property.getPropertyRentalPrice());
            }
        }
    }

    /**
     * Handles the sale of a property.
     * @param player - Player selling the property.
     * @param property - Property being sold.
     */
    public void propertySell(final Player player, final Square property){
        player.updateBalance(property.getPropertyPurchasePrice());
        player.removeProperty(property);
        property.updateOwner(null);
    }
    /**
     * Returns whether the property is owned or not.
     * @param property - Property being checked.
     * @return boolean representing ownership.
     */
    public Boolean propertyOwned(final Square property){
        return (property.getPropertyOwner() == null);
    }
}
