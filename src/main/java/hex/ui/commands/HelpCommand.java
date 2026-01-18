package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * command to print the help message.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class HelpCommand extends HexCommand {
    private static final String COMMAND_NAME = "help";
    private static final String COMMAND_DESCRIPTION = "Prints this help message";
    private static final String HELP_SUCCESS_MESSAGE = "* %s: %s%n";
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    /**
     * Instantiates a help command.
     *
     * @param inputHandler The input handler to use
     * @param hex          The hex game to use
     * @param hub          The hub to use
     */
    public HelpCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        Map<String, String> commands = inputHandler.getCommandsGuide();

        List<String> sortedCommands = new ArrayList<>(commands.keySet());
        Collections.sort(sortedCommands);

        for (String commandName : sortedCommands) {
            String commandDescription = commands.get(commandName);
            System.out.printf(HELP_SUCCESS_MESSAGE, commandName, commandDescription);
        }
        return true;
    }
}
