package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * command to list all games.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class ListGamesCommand extends HexCommand {

    private static final String COMMAND_NAME = "list-games";
    private static final String COMMAND_DESCRIPTION = "Lists all games that are currently running";
    private static final String SUCCESS_MESSAGE = "%s: %d%n";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    /**
     * Instantiates a list-games command.
     *
     * @param inputHandler The input handler to use
     * @param hex          The hex game to use
     * @param hub          The hub to use
     */
    public ListGamesCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        Map<String, Hex> gamesWithName = hub.getGamesWithName();
        Map<String, Integer> currentTurnMap = new LinkedHashMap<>();
        // maps games to their current turn
        for (Entry<String, Hex> entry : gamesWithName.entrySet()) {
            String gameName = entry.getKey();
            Hex hexGame = entry.getValue();

            int currentTurn = hexGame.getCurrentTurn();

            currentTurnMap.put(gameName, currentTurn);
        }
        // prints the games and their current turns that are not won
        for (Entry<String, Integer> entry : currentTurnMap.entrySet()) {
            String gameName = entry.getKey();
            int currentTurn = entry.getValue();

            Hex gameToShow = gamesWithName.get(gameName);
            if (!gameToShow.isGameWon()) {
                String formattedOutput = String.format(SUCCESS_MESSAGE, gameName, currentTurn);
                System.out.print(formattedOutput);
            }
        }
        return true;
    }

}
