package com.cm6123.monopoly.app;

import com.cm6123.monopoly.game.Board;
import com.cm6123.monopoly.game.Player;
import com.cm6123.monopoly.game.Rules;
import com.cm6123.monopoly.game.Square;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;


public final class Application {
    /**
     * Create a logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(Application.class);


    private Application() {
    }

    /**
     * Stores the size of the board.
     */
    private Integer boardSize;

    /**
     * Stores the number of players playing.
     */
    private Integer playerCount;

    /**
     * Stores the number of rounds remaining.
     */
    private Integer roundsLeft;

    /**
     * Asks a questiona and checks the inputs for the question are valid.
     * @param question - Question being asked
     * @param lower - The lowest possible bound of response.
     * @param upper - The highest possible bound of response.
     * @return integer of user's response.
     */
    public Integer validate(final String question, final Integer lower, final Integer upper){
        Scanner scan = new Scanner(System.in);
        Integer response;
        System.out.println(question);
        if (scan.hasNextInt()) {
            response = scan.nextInt();
            if (response >= lower && response <= upper){
                return response;
            } else{
                System.out.println("Please enter a valid response.");
                validate(question, lower, upper);
            }
        } else{
            System.out.println("Please enter a valid response.");
            validate(question, lower, upper);
        }
        return 1;
    }

    /**
     * Gets game specifics from user.
     */
    public void userInputs(){
        this.playerCount = validate("How many people are playing? (2 - 10)",2,10);
        this.roundsLeft = validate("And for how many rounds? (3 - 15)",3,15);
        this.boardSize = validate("How big should the board be? (10 - 50)",10,50);
    }
    /**
     * Create a new board using the inputted rules.
     * @param rule - Stores size of board, number of players, etc.
     * @return fully initialised board.
     */
    public Board initBoard(final Rules rule){
        Board gameBoard = new Board(rule.getBoardSize());
        gameBoard.calculateSizing();
        gameBoard.fillBoard();
        gameBoard.readAllSquares();
        gameBoard.createBoard();
        return gameBoard;
    }


    /**
     * View user's current square.
     * @param board - Board being used.
     * @param playerId - ID of player.
     * @param rules - Instance of rules class.
     */
    public void viewSquare(final Rules rules, final Board board, final Integer playerId){
        Player player = rules.returnPlayers().get(playerId - 1);
        Integer position = player.getPlayerPosition();
        board.readSquareType(position);
        if (board.readSquareType(position) == "Train Station"){
            System.out.println("Station Name : "+board.readAllSquares().get(position-1).getName());
        }
    }

    /**
     * Prints the balance of a specific player.
     * @param playerId - Player whose balance is being checked.
     * @param rules - Instance of rules class.
     */
    public void viewBalance(final Rules rules, final Integer playerId){
        Player player = rules.returnPlayers().get(playerId - 1);
        System.out.println("Your balance is £"+Math.round(player.returnBalance()*100)/100);
    }


    /**
     * Prints the board to the console.
     * @param board - Board being printed.
     */
    public void printBoard(final Board board){
        for(List<String> row : board.returnGrid()){
            System.out.println(row);
        }
    }

    /**
     * Returns the properties owned by a player.
     * @param rules - Instance of rules class.
     * @param playerId - ID of player.
     * @return List of properties owned by player.
     */
    public List<Square> getProperties(final Rules rules, final Integer playerId){
        List<Square> owned = rules.returnPlayers().get(playerId - 1).viewProperties();
        for (Square square : owned){
            System.out.println("-------------------");
            System.out.println(square.getName());
            System.out.println("Purchased for Â£"+square.getPropertyPurchasePrice());
            System.out.println("Rent is Â£"+square.getPropertyRentalPrice());
            System.out.println("-------------------");
        }
        return rules.returnPlayers().get(playerId - 1).viewProperties();
    }

    /**
     * main entry point.  If args provided, result is displayed and program exists. Otherwise, user is prompted for
     * input.
     *
     * @param args command line args.
     */
    public static void main(final String[] args) {
        logger.info("Application Started with args:{}", String.join(",", args));
        System.out.println("Welcome to Monopoly!");
        System.out.println("The rules are simple. Squares on the grid represent roads. 'P's are properties, 'T's are train stations, and 'O's are tax offices.");
        System.out.println("You will be given £1000 to begin with. You can earn money by buying properties and charging rent; you lose money by landing on other people's properties, landing on stations or offices.");
        System.out.println("Offices will charge a percentage of your current balance depending on what you roll.");
        System.out.println("Train stations will charge their base cost multiplied by the sum of your roll.");
        System.out.println("The game will end after either a set amount of rounds or when only one player remains.");
        System.out.println("The winner is the player with the highest asset value - bank balance + property value.");
        System.out.println("Good luck!");
        System.out.println("---------------");
        Scanner scanner = new Scanner(System.in);
        Application app = new Application();
        // Gather inputs from the user and create the game board and players.
        app.userInputs();
        Rules rules = new Rules(app.playerCount, app.roundsLeft, app.boardSize);
        for (int i = 1; i <= app.playerCount; i++){
            System.out.println("What is the name of Player " + i +"?");
            String name = scanner.nextLine();
            rules.createPlayer(name, i);
        }
        Board gameBoard = app.initBoard(rules);
        // Main game loop
        for (int j = 0; j < app.roundsLeft; j++) {
            for (int i = 1; i <= app.playerCount; i++) {
                // Print game board
                System.out.println();
                app.printBoard(gameBoard);
                System.out.println();
                // Begin player's turn, give them option on what to do + handle
                // accordingly.
                System.out.println("It's " + rules.returnPlayers().get(i - 1).getPlayerName() + "'s turn!");
                System.out.println("Would you like to Roll (R), View Your Property Details (P), View Your Balance (B), or View Your Current Square Details (D)?");
                String choice = scanner.nextLine();
                if (choice.toUpperCase().equals("R")) {
                    // Move the player, then read their square type and act
                    // accordingly.
                    rules.takeTurn(gameBoard, i);
                    Integer playerPosition = rules.returnPlayers().get(i-1).getPlayerPosition();
                    String currentSquareType = gameBoard.readSquareType(playerPosition);
                    Square currentPosition = gameBoard.readAllSquares().get(playerPosition - 1);
                    Player currentPlayer = rules.returnPlayers().get(i-1);
                    if (currentSquareType.equals("Property")){
                        // Handling for if property is owned or not.
                        // Will either offer purchase or force rent.
                        if(gameBoard.propertyOwned(currentPosition)){
                            System.out.println("Property Name: " + currentPosition.getName());
                            System.out.println("Property Purchase Cost: " + currentPosition.getPropertyPurchasePrice());
                            System.out.println("Property Rental Cost: " + currentPosition.getPropertyRentalPrice());
                            if (currentPosition.getPropertyOwner() != null){
                                System.out.println("Property Owner: Player " + currentPosition.getPropertyOwner());
                            }
                            System.out.println("Would you like to purchase this property? Y/N");
                            if (scanner.nextLine().toUpperCase().equals("Y")){
                                gameBoard.propertyPurchase(currentPosition, currentPlayer, rules);
                                System.out.println("You have purchased "+currentPosition.getName());
                            } else{
                                System.out.println("You decided to not buy the property.");
                            }
                        } else{
                            gameBoard.propertyRent(currentPosition, currentPlayer, rules);
                        }
                    } else if (currentSquareType.equals("Train Station")) {
                        // Handling for landing on train station.
                        System.out.println("You landed on "+currentPosition.getName()+", a train station.");
                        currentPosition.returnStationPayment(currentPlayer.getRecentRoll(), currentPlayer);
                    } else if(currentSquareType.equals("Tax Office")){
                        // Handling for landing on tax office.
                        currentPosition.returnOfficePayment(currentPlayer, currentPlayer.getRecentRoll(), currentPlayer.getRolledDouble());
                    }
                    while(rules.checkBankruptcy(i)) {
                        System.out.println("You have gone bankrupt! Would you like to sell a property? Y/N");
                        if (scanner.nextLine().toUpperCase().equals("Y")) {
                            List<Square> owned = app.getProperties(rules, i);
                            if (owned.size() == 0){
                                System.out.println("You have gone bankrupt and have no properties to sell.");
                                System.out.println("You are out of the game!");
                                rules.removePlayer(i);
                                app.playerCount --;
                                i--;
                                break;
                            } else{
                                System.out.println("Which property would you like to sell?");
                                try{
                                    Square propertyToSell = null;
                                    String propertyChoice = scanner.nextLine();
                                    for (Square property : owned){
                                        if (property.getName().equals(propertyChoice)) {
                                            propertyToSell = property;
                                        }
                                    }
                                    gameBoard.propertySell(currentPlayer, propertyToSell);
                                    System.out.println("You have sold " + propertyToSell.getName());
                                } catch (Exception e){
                                    System.out.println("You have entered an invalid property name.");
                                }
                            }
                        } else {
                            System.out.println("You have gone bankrupt and have no properties to sell.");
                            System.out.println("You are out of the game!");
                            rules.removePlayer(i);
                            app.playerCount --;
                            i--;
                            break;
                        }
                    }
                } else if(choice.toUpperCase().equals("D")){
                    app.viewSquare(rules, gameBoard, i);
                    i --;
                } else if(choice.toUpperCase().equals("B")){
                    app.viewBalance(rules, i);
                    i --;
                } else if(choice.toUpperCase().equals("P")){
                    List<Square> owned = app.getProperties(rules, i);
                    i--;
                } else{
                    i--;
                }
            }
            // Handling for when only one player is remaining
            if (app.playerCount == 1){
                break;
            }
        }
        List<Integer> winners = rules.declareWinner();
        for (Integer winner : winners){
            for (Player player : rules.returnPlayers()){
                if (player.getPlayerNumber() == winner){
                    System.out.println("The winner is "+player.getPlayerName()+" with a total asset value of "+Math.round(player.returnBalance()*100)/100+"!");
                }
            }
        }
        logger.info("Application closing");
    }
}
