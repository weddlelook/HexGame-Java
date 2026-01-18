package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

/**
 * command to swap tokens.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class SwapCommand extends HexCommand {

    private static final String COMMAND_NAME = "swap";
    private static final String COMMAND_DESCRIPTION = "Swaps the tokens of player. Usable only on the second turn";
    private static final String INVALID_TURN_MESSAGE = createError("Only second player can swap only on the second turn.");
    private static final String SWAP_SUCCESS_MESSAGE = "%s swaps%n";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    /**
     * Instantiates a swap command.
     *
     * @param inputHandler The input handler to use
     * @param hex          The hex game to use
     * @param hub          The hub to use
     */
    public SwapCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        int currentTurn = hex.getCurrentTurn();

        if (currentTurn == 1 && hex.getPlayerPointer() == 1) {
            String playerName = hex.getCurrentPlayer().getName();
            System.out.printf(SWAP_SUCCESS_MESSAGE, playerName);
            hex.swap();
            return true;
        } else {
            // gives error if current turn or player is not suitable for a swap
            System.err.println(INVALID_TURN_MESSAGE);
            return false;
        }
    }
}
