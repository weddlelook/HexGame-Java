package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

/**
 * command to print the history.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class HistoryCommand extends HexCommand {

    private static final String COMMAND_NAME = "history";
    private static final String COMMAND_DESCRIPTION = "Prints the coordinates of the token(s) placed on the last turn(s)";
    private static final String INVALID_TURN_ARGUMENT_MESSAGE = createError("Invalid turn argument. Expected a integer.");
    private static final String INVALID_TURN_NUMBER_MESSAGE = createError("Invalid turn number. Expected a value between 1 and %d.%n");
    private static final String NONEXISTENT_HISTORY_ERROR = createError("There is no history to show yet.");
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final int PAST_TURNS_INDEX = 0;

    /**
     * instantiates a history command.
     *
     * @param inputHandler input handler to use
     * @param hex          hex game to use
     * @param hub          hub to use
     */
    public HistoryCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    public boolean execute(String[] commandArguments) {
        if (commandArguments.length > EXPECTED_NUMBER_OF_ARGUMENTS) {
            System.err.printf(EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT, EXPECTED_NUMBER_OF_ARGUMENTS, commandArguments.length);
            return false;
        }
        return executeHexCommand(commandArguments);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        int pastTurns;

        if (commandArguments.length == 0) {
            pastTurns = 1;
        } else {
            // gives error if the argument isn't a number
            try {
                pastTurns = Integer.parseInt(commandArguments[PAST_TURNS_INDEX]);
            } catch (NumberFormatException e) {
                System.err.println(INVALID_TURN_ARGUMENT_MESSAGE);
                return false;
            }
        }
        int currentTurn = hex.getCurrentTurn();
        // gives error if there is no history yet
        if (currentTurn == 0) {
            System.err.println(NONEXISTENT_HISTORY_ERROR);
            return false;
        }
        // gives error if the turn number is out of bounds
        if (pastTurns < 1 || pastTurns > currentTurn) {
            System.err.printf(INVALID_TURN_NUMBER_MESSAGE, currentTurn);
            return false;
        }

        for (int i = currentTurn - 1; i >= currentTurn - pastTurns; i--) {
            System.out.println(hex.getHistory().get(i));
        }
        return true;
    }
}
