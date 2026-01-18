package hex.model;

import hex.model.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class represents a hex game.
 *
 * @author unxgx
 */
public class Hex {

    private static final String HISTORY_TEMPLATE = "%s: %d %d";

    private final String name;
    private int playerPointer;
    private int currentTurn = 0;
    private final List<Player> players = new ArrayList<>();
    private final Map<Integer, String> history;
    private final Board board;
    private boolean gameWon = false;
    private boolean swapped = false;

    /**
     * initializes a new hex game.
     *
     * @param sideLength side length of the board
     * @param playerOne  player one
     * @param playerTwo  player two
     * @param name       name of the game
     */
    public Hex(int sideLength, Player playerOne, Player playerTwo, String name) {
        this.board = new Board(sideLength);
        this.players.add(playerOne);
        this.players.add(playerTwo);
        this.history = new HashMap<>();
        this.name = name;
    }

    /**
     * Sets the current player's token on the specified cell and records the move in the game history.
     * It also switches to the next player's turn.
     *
     * @param xCoordinate The x-coordinate of the cell.
     * @param yCoordinate The y-coordinate of the cell.
     */
    public void set(int xCoordinate, int yCoordinate) {
        board.set(xCoordinate, yCoordinate, getCurrentPlayer().getToken());
        history.put(currentTurn, String.format(HISTORY_TEMPLATE, getCurrentPlayer().getName(), xCoordinate, yCoordinate));
        nextPlayer();
        nextTurn();
    }

    private void nextPlayer() {
        playerPointer = (playerPointer + 1) % players.size();
    }

    private void nextTurn() {
        currentTurn++;
    }

    /**
     * Swaps the player tokens, changing the game's token order. It also adjusts the history accordingly.
     */
    public void swap() {
        players.get(0).setToken(Entry.O);
        players.get(1).setToken(Entry.X);
        this.swapped = true;

        String firstMoveHistory = getHistory().get(0);
        String[] parts = firstMoveHistory.split(":");
        String firstMove = parts[1].trim();
        String[] firstMoveString = firstMove.split(" ");
        int[] firstMoveCoordinates = new int[firstMoveString.length];

        for (int i = 0; i < firstMoveString.length; i++) {
            firstMoveCoordinates[i] = Integer.parseInt(firstMoveString[i]);
        }
        history.put(0, String.format(HISTORY_TEMPLATE, getCurrentPlayer().getName(), firstMoveCoordinates[0], firstMoveCoordinates[1]));
        nextPlayer();
    }

    /**
     * Evaluates the winner of the game and returns the winning player. If there is no winner, it returns null.
     *
     * @return The winning player or null if no winner is found.
     */
    public Player evaluateWinner() {
        nextPlayer(); // getting the last player who placed a token
        Entry tokenToEvaluate = getCurrentPlayer().getToken();
        boolean hasWon = false;

        if (tokenToEvaluate == Entry.X) {
            hasWon = board.nordToSouthAlgorithm();
        } else if (tokenToEvaluate == Entry.O) {
            hasWon = board.westToEastAlgorithm();
        }

        if (hasWon) {
            this.gameWon = true;
            return getCurrentPlayer();
        } else {
            nextPlayer(); // if no one has won, the player pointer has to be reset
            return null;
        }
    }

    /**
     * Sets the winning path on the game board back to the winning token. This method is used after a player has won.
     */
    public void setWinningPathToBack() {
        board.setWinningPathToBack(getCurrentPlayer().getToken());
    }

    /**
     * Gets a copy of the game board.
     *
     * @return a copy of the game board
     */
    public Board getBoard() {
        return board.copy();
    }

    /**
     * Checks if a specified cell on the game board is empty.
     *
     * @param row x-coordinate index of the cell.
     * @param col y-coordinate index of the cell.
     * @return True if the cell is empty, otherwise false.
     */
    public boolean isEmpty(int row, int col) {
        return board.isEmpty(row, col);
    }

    /**
     * Gets the name of the game.
     *
     * @return The name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * return if the game is over, for situations where changed to a won game.
     *
     * @return (@code gamewon)
     */
    public boolean isGameWon() {
        return this.gameWon;
    }

    /**
     * Gets the current player.
     *
     * @return The current player taking their turn.
     */
    public Player getCurrentPlayer() {
        return players.get(playerPointer);
    }

    /**
     * Gets the game history, which includes all player moves.
     *
     * @return An unmodifiable map of game history entries.
     */
    public Map<Integer, String> getHistory() {
        return Collections.unmodifiableMap(this.history);
    }

    /**
     * Gets the current turn number in the game.
     *
     * @return The current turn number.
     */
    public int getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * Gets the index of the current player in the list of players.
     *
     * @return The index of the current player.
     */
    public int getPlayerPointer() {
        return this.playerPointer;
    }

    /**
     * returns wheter player tokens have been swapped.
     *
     * @return (@code swapped)
     */
    public boolean isSwapped() {
        return this.swapped;
    }

}
