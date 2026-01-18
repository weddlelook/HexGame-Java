package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

import java.util.Map;

/**
 * command to start a new game.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class NewGameCommand extends HexCommand {

    private static final String COMMAND_NAME = "new-game";
    private static final String COMMAND_DESCRIPTION = "Starts a new game";
    private static final String ALREADY_EXISTING_NAME_ERROR = createError("The name %s is already used for an another game%n");
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final int NEW_GAME_NAME_INDEX = 0;

    /**
     * Instantiates a new-game command.
     *
     * @param inputHandler The input handler to use
     * @param hex          The hex game to use
     * @param hub          The hub to use
     */
    public NewGameCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        String newGameName = commandArguments[NEW_GAME_NAME_INDEX];
        Map<String, Hex> gamesWithName = hub.getGamesWithName();
        // gives error if the name is already used for an another game
        if (gamesWithName.containsKey(newGameName)) {
            System.err.printf(ALREADY_EXISTING_NAME_ERROR, newGameName);
            return false;
        }

        hub.closeGame(hub.getCurrentGame());
        Hex newGame = hub.createGame(newGameName);
        hub.startGame(newGame);

        return true;
    }
}
