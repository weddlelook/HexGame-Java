package hex.model.entity;

import hex.model.Entry;
import hex.model.Hex;

/**
 * This interface represents a player in a game of Hex. Implementing classes or objects
 * should provide functionality for obtaining player information and moves.
 *
 * @author unxgx
 */
public interface Player {

    /**
     * Gets the token (X or O) associated with this player.
     *
     * @return The player's token (X or O).
     */
    Entry getToken();

    /**
     * Sets the token (X or O) associated with this player.
     *
     * @param token The player's token (X or O) to be set.
     */
    void setToken(Entry token);

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    String getName();

    /**
     * Gets the move to be made by the player.
     *
     * @return The player's move represented as a string.
     */
    String getMove();

    /**
     * Sets the current hex game for the player.
     *
     * @param hex The current hex game in which the player is making a move.
     */
    void setCurrentHex(Hex hex);
}
