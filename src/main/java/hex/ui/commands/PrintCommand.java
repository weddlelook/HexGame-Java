package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

/**
 * command to print the board.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class PrintCommand extends HexCommand {
    private static final String COMMAND_NAME = "print";
    private static final String COMMAND_DESCRIPTION = "Prints the current board";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    /**
     * instantiates a print command.
     *
     * @param inputHandler input handler to use
     * @param hex          hex game to use
     * @param hub          hub to use
     */
    public PrintCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        System.out.print(hex.getBoard());
        return true;
    }
}
