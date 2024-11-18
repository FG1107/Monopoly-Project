package com.cm6123.monopoly.game;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rules {

    /**
     * Stores the size of the board.
     */
    private final Integer boardSize;

    /**
     * Stores the number of players playing.
     */
    private Integer playerCount;

    /**
     * Stores the details of players playing.
     */
    private final List<Player> players = new ArrayList<>();

    /**
     * Stores the number of rounds remaining.
     */
    private Integer roundsLeft;

    /**
     * Constructor that only takes in board size for testing.
     * @param boardInput - Desired size of board.
     */
    public Rules(final Integer boardInput){
        this.boardSize = boardInput;
    }
    /**
     * Takes inputted rules and stores them.
     * @param playerInput - Inputted number of players.
     * @param roundInput - Inputted number of rounds.
     * @param boardInput - Inputted number of board squares.
     */
    public Rules(final Integer playerInput, final Integer roundInput, final Integer boardInput){
        this.playerCount = playerInput;
        this.roundsLeft = roundInput;
        this.boardSize = boardInput;
    }

    /**
     * Returns the number of players playing.
     * @return integer value of total players.
     */
    public Integer getPlayerCount(){
        return this.playerCount;
    }

    /**
     * Returns the size of the board.
     * @return integer value of total board size.
     */
    public Integer getBoardSize(){
        return this.boardSize;
    }

    /**
     * Returns the number of rounds remaining.
     * @return integer value of rounds.
     */
    public Integer getRoundsLeft(){
        return this.roundsLeft;
    }

    /**
     * Returns the list of players currently active.
     * @return list of players.
     */
    public List<Player> returnPlayers(){
        return List.copyOf(this.players);
    }
    /**
     * Initialise a new board for use in the game.
     * @return Board created according to rules.
     */
    public Board createBoard(){
        Board gameBoard = new Board(this.boardSize);
        gameBoard.fillBoard();
        gameBoard.readAllSquares();
        return gameBoard;
    }

    /**
     * Create a new player and add it to list of players.
     * @param playerName - Name of player being created.
     * @param playerNumber - Number of player being created.
     */
    public void createPlayer(final String playerName, final Integer playerNumber){
        Player newPlayer = new Player(playerName, playerNumber);
        newPlayer.updateBalance(1000.0);
        players.add(newPlayer);
    }
    /**
     * Remove a player from the game.
     * @param playerId - ID of player being removed.
     */
    public void removePlayer(final Integer playerId){
        players.remove(playerId - 1);
    }
    /**
     * Function that involes taking a player's turn - moving them, paying rent, etc.
     *
     * @param playerId - ID of player whose turn it is.
     * @param board    - Board being used.
     * @return
     */
    public void takeTurn(final Board board, final Integer playerId){
        Player player = players.get(playerId - 1);
        System.out.println("You are on square " + player.getPlayerPosition());
        DiceSet dice = new DiceSet(new Dice(6));
        dice.completeRoll();
        List<Integer> rollsList = dice.asList();
        Boolean isDouble = dice.checkDouble();
        Integer roll = dice.sum();
        player.updatePlayerPosition(board, roll, isDouble);
        System.out.println("You rolled "+roll+"! Now you are on square "+player.getPlayerPosition());
    }

    /**
     * Function that checks if a player has gone bankrupt.
     * @param playerId - ID of player being checked.
     * @return true or false depending on bank status.
     */
    public Boolean checkBankruptcy(final Integer playerId){
        Player player = players.get(playerId-1);
        if (player.returnBalance() <= 0){
            return true;
        }
        return false;
    }

    /**
     * Function used to declare a winner.
     * @return - Integer IDs of people with the most money.
     */
    public List<Integer> declareWinner(){
        List<Integer> winningPlayers = new ArrayList<>();
        List<Double> playerScores = new ArrayList<>();
        List<Integer> playerPropertyCounts = new ArrayList<>();
        for (Player playerScore : players){
            playerScores.add(playerScore.returnBalance() + playerScore.calculatePropertiesValue());
        }
        Double biggestScore = Collections.max(playerScores);
        for (Player player : players){
            if (player.returnBalance().equals(biggestScore)){
                winningPlayers.add(player.getPlayerNumber());
            }
        }
        return winningPlayers;
    }
}
