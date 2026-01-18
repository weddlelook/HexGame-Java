package hex.model.entity;

import hex.model.Hex;
import hex.model.Entry;

/**
 * This class represents a human player in a game of Hex. Human players have a name
 * and a token (X or O), but they do not implement automated moves or game logic.
 * This class is intended to be used for manual interaction with human players.
 *
 * @author unxgx
 */
public final class HumanPlayer implements Player {

    private static final String NOT_IMPLEMENTED_MESSAGE = "Not Implemented";
    private final String name;
    private Entry token;

    /**
     * Instantiates a new human player with the specified name and token.
     *
     * @param name  The name of the human player.
     * @param token The token (X or O) associated with the human player.
     */
    public HumanPlayer(String name, Entry token) {
        this.name = name;
        this.token = token;
    }

    @Override
    public Entry getToken() {
        return this.token;
    }

    @Override
    public void setToken(Entry token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getMove() {
        /* This method is not implemented for human players. And it is guaranteed to never be called.
         * The method is only here to satisfy the interface. Using of interface was beneficial because of polymorphism.
         * Using a Player collection, we can store both HumanPlayer and AIPlayer objects in the same collection.
         */
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override
    public void setCurrentHex(Hex hex) {
        /* This method is not implemented for human players. And it is guaranteed to never be called.
         * The method is only here to satisfy the interface. Using of interface was beneficial because of polymorphism.
         * Using a Player collection, we can store both HumanPlayer and AIPlayer objects in the same collection.
         */
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_MESSAGE);
    }
}
