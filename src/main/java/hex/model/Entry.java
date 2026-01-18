package hex.model;

/**
 * Represents entries of the {@link Board}.
 *
 * @author Programmieren-Team
 * @author unxgx
 */
public enum Entry {

    /**
     * Represents an empty cell on the game board.
     */
    EMPTY('.'),

    /**
     * Represents a cell containing 'X' as the token.
     */
    X('X'),

    /**
     * Represents a cell containing 'O' as the token.
     */
    O('O'),

    /**
     * Represents a special token '*' used to mark a cell as part of a winning path.
     */
    WINNING('*');

    private final char token;

    /**
     * Constructs an `Entry` enum constant with the specified token character.
     *
     * @param token The character representing the token.
     */
    Entry(char token) {
        this.token = token;
    }

    /**
     * Gets the character token associated with this Entry.
     *
     * @return The token.
     */
    public char getToken() {
        return token;
    }

}


