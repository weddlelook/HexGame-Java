package hex.ui.commands;

import hex.ui.InputHandler;
import hex.ui.Command;

/**
 * command to quit the game.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class QuitCommand extends Command {
    private static final String COMMAND_NAME = "quit";
    private static final String COMMAND_DESCRIPTION = "Quit all games and end program";
    private static final String QUIT_WITH_ARGUMENTS_ERROR = "Error: quit does not allow args.";

    /**
     * Instantiates a quit command.
     *
     * @param inputHandler The input handler to use
     */
    public QuitCommand(InputHandler inputHandler) {
        super(COMMAND_NAME, inputHandler, COMMAND_DESCRIPTION);
    }

    @Override
    public boolean execute(String[] commandArguments) {
        if (commandArguments.length != 0) {
            System.err.println(QUIT_WITH_ARGUMENTS_ERROR);
            return false;
        }
        inputHandler.quit();
        return true;
    }
}
