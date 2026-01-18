package hex.model;

import hex.ui.InputHandler;
import hex.model.entity.Player;

import java.util.HashMap;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents a control center that manages the hex games.
 * It provides an interface above games, allowing to create, switch and start hex games.
 *
 * @author unxgx
 */
public class Hub {

    private static final String FIRST_GAME_NAME = "Prime";
    private final int sideLength;
    private Hex currentGame;
    private final Map<Hex, InputHandler> games;
    private final Map<String, Hex> gamesWithName;
    private final Player playerOne;
    private final Player playerTwo;
    private final boolean autoPrint;
    private final boolean useAI;


    /**
     * creates a new hub.
     *
     * @param sideLength side length of the hex games
     * @param playerOne  player one
     * @param playerTwo  player two
     * @param autoPrint  whether the board should be printed automatically
     * @param useAI      whether the AI should be used
     */
    public Hub(int sideLength, Player playerOne, Player playerTwo, boolean autoPrint, boolean useAI) {
        this.sideLength = sideLength;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.autoPrint = autoPrint;
        this.games = new HashMap<>();
        this.gamesWithName = new LinkedHashMap<>();
        this.useAI = useAI;
        createGame(FIRST_GAME_NAME);
    }

    /**
     * Starts the initial game, with the name "Prime".
     */
    public void start() {
        Hex startingGame = gamesWithName.get(FIRST_GAME_NAME);
        startGame(startingGame);
    }

    /**
     * Creates a new Hex game with the specified name and adds it to the lists of managed games.
     *
     * @param name the name of the game
     * @return the created game
     */
    public Hex createGame(String name) {
        Hex newGame = new Hex(sideLength, playerOne, playerTwo, name);
        InputHandler inputHandler = new InputHandler(newGame, autoPrint, this, useAI);
        games.put(newGame, inputHandler);
        gamesWithName.put(name, newGame);
        return newGame;
    }

    /**
     * Starts the specified Hex game.
     *
     * @param game the game to start
     */
    public void startGame(Hex game) {
        InputHandler inputHandler = games.get(game);
        currentGame = game;
        if (useAI) {
            playerTwo.setCurrentHex(currentGame);
        }
        adjustTokens(game);
        inputHandler.interact();
    }

    /**
     * Closes the specified Hex game.
     *
     * @param game the game to close
     */
    public void closeGame(Hex game) {
        InputHandler inputHandler = games.get(game);
        currentGame = null;
        inputHandler.quit();
    }

    /**
     * Switches the active game to the one with the given name.
     *
     * @param name the name of the game to switch to
     */
    public void switchGame(String name) {
        Hex newGame = gamesWithName.get(name);
        closeGame(currentGame);
        InputHandler inputHandler = games.get(newGame);
        currentGame = newGame;
        if (useAI) {
            playerTwo.setCurrentHex(currentGame);
        }
        adjustTokens(newGame);
        inputHandler.interact(true);
    }

    /**
     * resets the tokens of the players according to the game.
     * @param game the game to adjust the tokens for
     */
    private void adjustTokens(Hex game) {
        if (!game.isSwapped()) {
            playerOne.setToken(Entry.X);
            playerTwo.setToken(Entry.O);
        } else {
            playerOne.setToken(Entry.O);
            playerTwo.setToken(Entry.X);
        }
    }

    /**
     * Returns an unmodifiable map of games with their names as keys.
     *
     * @return An unmodifiable map of games with their names.
     */
    public Map<String, Hex> getGamesWithName() {
        return Collections.unmodifiableMap(this.gamesWithName);
    }

    /**
     * Returns the currently active Hex game.
     *
     * @return The currently active Hex game.
     */
    public Hex getCurrentGame() {
        return this.currentGame;
    }

}
