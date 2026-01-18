package hex.model.entity;

import hex.model.Hex;
import hex.model.Entry;

/**
 * This class represents an abstract artificial player in a game of Hex. Artificial players have a name
 * and a token (X or O) and implement automated moves or game logic.
 * Subclasses of this class are responsible for implementing the specific move algorithm  for the artificial player.
 *
 * @author unxgx
 */
public abstract class ArtificialPlayer implements Player {

    protected static final String PLACE_COMMAND = "place %d %d";
    protected static final String PLACE_SUCCESSFUL = "%s places at %d %d%n";
    protected static final String SWAP_COMMAND = "swap";

    protected Hex currentHex;
    private final String name;
    private Entry token;

    /**
     * Instantiates a new artificial player with the specified name and token.
     *
     * @param name  The name of the artificial player.
     * @param token The token (X or O) associated with the artificial player.
     */
    protected ArtificialPlayer(String name, Entry token) {
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
    public abstract String getMove();

    @Override
    public void setCurrentHex(Hex hex) {
        this.currentHex = hex;
    }
}
