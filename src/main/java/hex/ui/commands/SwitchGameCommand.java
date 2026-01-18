package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

import java.util.Map;

/**
 * command to switch the current game to another game.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class SwitchGameCommand extends HexCommand {

    private static final String COMMAND_NAME = "switch-game";
    private static final String COMMAND_DESCRIPTION = "Switchs the current game with the specified game";
    private static final String NON_EXISTENT_GAME_ERROR = createError("The game %s does not exist%n");
    private static final String SUCCESS_MESSAGE = "Switched to %s%n";
    private static final String SWITCH_REQUEST_TO_CURRENT_GAME_ERROR = createError("You are already playing the game %s%n");
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final int NEW_GAME_NAME_INDEX = 0;

    /**
     * Instantiates a switch-game command.
     *
     * @param inputHandler The input handler to use
     * @param hex          The hex game to use
     * @param hub          The hub to use
     */
    public SwitchGameCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        String newGameName = commandArguments[NEW_GAME_NAME_INDEX];
        Map<String, Hex> gamesWithName = hub.getGamesWithName();
        // gives error if the game does not exist
        if (!gamesWithName.containsKey(newGameName)) {
            System.err.printf(NON_EXISTENT_GAME_ERROR, newGameName);
            return false;
        }
        // gives error if the game is already the current game
        if (newGameName.equals(hub.getCurrentGame().getName())) {
            System.out.printf(SWITCH_REQUEST_TO_CURRENT_GAME_ERROR, newGameName);
            return false;
        }

        System.out.printf(SUCCESS_MESSAGE, newGameName);
        hub.switchGame(newGameName);
        return true;

    }

}
