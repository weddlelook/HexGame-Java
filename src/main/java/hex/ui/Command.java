package hex.ui;

import java.util.Objects;

/**
 * A command that can be executed by the user.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public abstract class Command {

    private static final String ERROR_PREFIX = "Error: ";
    protected final InputHandler inputHandler;
    private final String commandName;
    private final String commandDescription;

    /**
     * Instantiates a new command.
     *
     * @param commandName        The name of the command
     * @param inputHandler       The input handler to use
     * @param commandDescription The description of the command
     */
    protected Command(String commandName, InputHandler inputHandler, String commandDescription) {
        this.commandName = Objects.requireNonNull(commandName);
        this.inputHandler = Objects.requireNonNull(inputHandler);
        this.commandDescription = Objects.requireNonNull(commandDescription);
    }

    /**
     * Gets the command name.
     *
     * @return the command name
     */
    public final String getCommandName() {
        return commandName;
    }

    /**
     * Gets the command description.
     *
     * @return the command description
     */
    public final String getCommandDescription() {
        return this.commandDescription;
    }

    /**
     * Executes the command with the given arguments.
     *
     * @param commandArguments The command arguments.
     * @return Whether the command was successful.
     */
    public abstract boolean execute(String[] commandArguments);

    /**
     * Creates an error message.
     *
     * @param message The message.
     * @return The message as error message.
     */
    protected static String createError(String message) {
        return ERROR_PREFIX + message;
    }
}
