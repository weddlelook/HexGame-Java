package hex.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * The command handler responsible for maintaining all neccessary commands and user interaction.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public abstract class CommandHandler {

    private static final String COMMAND_SEPERATOR_REGEX = "\\s+";
    private static final String NEXT_TURN_COMMANDS_REGEX = "^(swap|place|quit|new-game|switch-game)$";
    private static final String COMMAND_NOT_FOUND = "Error: Command %s not found%n";
    private static final String GAME_COMMAND_NOT_AVAILABLE = "Error: You can't use Command %s after winning%n";
    private final Map<String, Command> commands;
    private final Map<String, String> commandsGuide;
    private final List<String> gameCommands = new ArrayList<>(Arrays.asList("swap", "place"));

    /**
     * Instantiates a new command handler
     */
    protected CommandHandler() {
        this.commands = new HashMap<>();
        this.commandsGuide = new HashMap<>();
    }

    /**
     * Executes a specified command with its arguments.
     * This method takes a command with its arguments as input, processes it, and executes the corresponding command if it exists.
     * If the command is not found , an error message is printed.
     *
     * @param commandwithArguments
     * @return false, if the given command executed successfully and requires a new turn. true, if both conditions are not met.
     */
    protected boolean executeCommand(String commandwithArguments) {
        String[] splittedCommand = commandwithArguments.trim().split(COMMAND_SEPERATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        boolean isPending = true;

        if (!commands.containsKey(commandName)) {
            System.err.printf(COMMAND_NOT_FOUND, commandName);
            return isPending;
        }

        boolean isSuccessful = commands.get(commandName).execute(commandArguments);
        boolean isNextTurnComand = commandName.matches(NEXT_TURN_COMMANDS_REGEX);
        // when the command requires a next turn, a command from user for that turn is not expected anymore
        if (isSuccessful && isNextTurnComand) {
            isPending = false;
        }
        return isPending;
    }

    /**
     * Executes a specified command with its arguments after a won game
     * These method doesn't allow to execute swap and place commands, in difference to executeCommand.
     *
     * @param commandwithArguments
     * @return false, if the given command executed successfully and requires a new turn. true, if both conditions are not met.
     */
    protected boolean executeCommandAfterWinning(String commandwithArguments) {
        String[] splittedCommand = commandwithArguments.trim().split(COMMAND_SEPERATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        boolean isPending = true;

        if (!commands.containsKey(commandName)) {
            System.err.printf(COMMAND_NOT_FOUND, commandName);
            return isPending;
        }
        if (gameCommands.contains(commandName)) {
            System.err.printf(GAME_COMMAND_NOT_AVAILABLE, commandName);
            return isPending;
        }

        boolean isSuccessful = commands.get(commandName).execute(commandArguments);
        boolean isNextTurnComand = commandName.matches(NEXT_TURN_COMMANDS_REGEX);

        if (isSuccessful && isNextTurnComand) {
            isPending = false;
        }
        return isPending;
    }

    /**
     * Maps all commands to their names.
     */
    protected abstract void initCommands();

    /**
     * Retrieves an unmodifiable view of the commands guide, which maps command names to their descriptions.
     *
     * @return An unmodifiable map containing command names as keys and their descriptions as values.
     */
    public Map<String, String> getCommandsGuide() {
        return Collections.unmodifiableMap(this.commandsGuide);
    }

    /**
     * Adds a new command to the collection of available commands. The command is mapped to its name, and its name to its description.
     *
     * @param command
     */
    protected void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
        this.commandsGuide.put(command.getCommandName(), command.getCommandDescription());
    }

}
