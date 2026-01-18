package hex.ui;

import hex.model.Hex;
import hex.model.Hub;

import java.util.Objects;

/**
 * A command used for handling {@link Hex} regarding interactions.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public abstract class HexCommand extends Command {

    protected static final String EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT = createError("Expected %d arguments but got %d%n");
    protected final Hex hex;
    protected final Hub hub;
    private final int expectedNumberOfArguments;


    /**
     * Creates a new hex game command.
     *
     * @param commandName               The name of the command
     * @param inputHandler              The input handler to use
     * @param hex                       The hex game to use
     * @param expectedNumberOfArguments The expected number of arguments
     * @param commandDescription        The description of the command
     * @param hub                       The hub to use
     */
    protected HexCommand(String commandName, InputHandler inputHandler, Hex hex, int expectedNumberOfArguments,
                         String commandDescription, Hub hub) {
        super(commandName, inputHandler, commandDescription);
        this.hex = Objects.requireNonNull(hex);
        this.expectedNumberOfArguments = expectedNumberOfArguments;
        this.hub = hub;
    }

    /**
     * Executes the command, if the number of arguments is correct.
     *
     * @param commandArguments The command arguments.
     * @return Whether the command was successful.
     */
    @Override
    public boolean execute(String[] commandArguments) {
        if (commandArguments.length != expectedNumberOfArguments) {
            System.err.printf(EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT, expectedNumberOfArguments, commandArguments.length);
            return false;
        }
        return executeHexCommand(commandArguments);
    }

    /**
     * Executes the command on the car sharing platform.
     *
     * @param commandArguments commandArguments The command arguments.
     * @return Whether the command was successful.
     */
    protected abstract boolean executeHexCommand(String[] commandArguments);


}
