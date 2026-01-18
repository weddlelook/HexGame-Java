package hex.ui;

import hex.model.Hex;
import hex.model.entity.Player;
import hex.ui.commands.HelpCommand;
import hex.ui.commands.SwapCommand;
import hex.ui.commands.NewGameCommand;
import hex.ui.commands.QuitCommand;
import hex.ui.commands.HistoryCommand;
import hex.ui.commands.SwitchGameCommand;
import hex.ui.commands.ListGamesCommand;
import hex.ui.commands.PlaceCommand;
import hex.ui.commands.PrintCommand;
import hex.model.Hub;

import java.util.Scanner;

/**
 * This class handles every user command for a given hex game.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class InputHandler extends CommandHandler {

    private static final String WELCOME_MESSAGE = "Welcome to %s%n";
    private static final String WINNER_MESSAGE = "%s wins!%n";
    private static final String INPUT_REQUEST = "%s's turn%n";

    private boolean running;
    private final Scanner scanner;
    private final Hex hex;
    private final Hub hub;
    private final boolean autoPrint;
    private final boolean useAI;
    private boolean isCalledBefore = false;


    /**
     * Creates a new instance.
     *
     * @param hex       the {@link Hex} game this instance shall be connected with
     * @param autoPrint whether to automatically print the board after each move
     * @param hub       the {@link Hub} this instance shall be connected with
     * @param useAI     whether to use an AI as second player or not
     */
    public InputHandler(Hex hex, boolean autoPrint, Hub hub, boolean useAI) {
        this.hex = hex;
        this.hub = hub;
        initCommands();
        scanner = new Scanner(System.in);
        this.autoPrint = autoPrint;
        this.useAI = useAI;

    }

    /**
     * Starts user interaction on console.
     *
     * @param switchCommand whether this is the first move after a switch command, in order not to print the board
     */
    public void interact(boolean switchCommand) {
        if (!isCalledBefore) {
            System.out.printf(WELCOME_MESSAGE, hex.getName());
        }
        this.isCalledBefore = true;
        this.running = true;
        boolean firstMoveAfterSwitch = switchCommand;

        while (running) {
            if (!hex.isGameWon()) {
                if (firstMoveAfterSwitch) {
                    handleUserInput(switchCommand);
                    firstMoveAfterSwitch = false;
                } else {
                    handleUserInput();
                }
                if (running) {
                    Player winner = hex.evaluateWinner();
                    if (winner != null) {
                        System.out.printf(WINNER_MESSAGE, winner.getName());
                        System.out.print(hex.getBoard());
                        hex.setWinningPathToBack();
                    }
                }
            } else {
                handleUserInputAfterWinning();
            }

        }
        scanner.close();
    }

    /**
     * a overload of interact, for the situation where it is not the first move after a switch command.
     */
    public void interact() {
        interact(false);
    }

    private void handleUserInputAfterWinning() {
        boolean pendingInput = true;

        while (pendingInput && scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            pendingInput = executeCommandAfterWinning(userInput);
        }
    }

    private void handleUserInput() {
        handleUserInput(false);
    }

    /**
     * Handles the user input.
     */
    private void handleUserInput(boolean switchCommand) {
        boolean pendingInput = true;
        if (!switchCommand) {
            if (this.autoPrint) {
                System.out.print(hex.getBoard());
            }
            System.out.printf(INPUT_REQUEST, hex.getCurrentPlayer().getName());
        }

        if (useAI && hex.getPlayerPointer() == 1) {
            String aiInput = hex.getCurrentPlayer().getMove();
            pendingInput = executeCommand(aiInput);
        }

        while (pendingInput && scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            pendingInput = executeCommand(userInput);
        }

    }

    @Override
    protected void initCommands() {
        this.addCommand(new QuitCommand(this));
        this.addCommand(new PlaceCommand(this, hex, hub));
        this.addCommand(new HelpCommand(this, hex, hub));
        this.addCommand(new PrintCommand(this, hex, hub));
        this.addCommand(new SwapCommand(this, hex, hub));
        this.addCommand(new HistoryCommand(this, hex, hub));
        this.addCommand(new ListGamesCommand(this, hex, hub));
        this.addCommand(new NewGameCommand(this, hex, hub));
        this.addCommand(new SwitchGameCommand(this, hex, hub));
    }

    /**
     * quits this hex game.
     */
    public void quit() {
        this.running = false;

    }

}
