package hex.ui.commands;

import hex.model.Hex;
import hex.model.Hub;
import hex.ui.HexCommand;
import hex.ui.InputHandler;

/**
 * command to place a token.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public final class PlaceCommand extends HexCommand {
    private static final String COMMAND_NAME = "place";
    private static final String COMMAND_DESCRIPTION = "Places a token on the board";
    private static final String INVALID_X_COORDINATE_MESSAGE = createError("Invalid x coordinate %d. Expected a value between 0 and %d.%n");
    private static final String INVALID_Y_COORDINATE_MESSAGE = createError("Invalid y coordinate %d. Expected a value between 0 and %d.%n");
    private static final String INVALID_COORDINATE_MESSAGE = createError("Invalid coordinates. Expected two integers.");
    private static final String OCCUPIED_FIELD_MESSAGE = createError("This field is already occupied.");
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final int X_COORDINATE_INDEX = 0;
    private static final int Y_COORDINATE_INDEX = 1;

    /**
     * instantiates a place command.
     *
     * @param inputHandler input handler to use
     * @param hex          hex game to use
     * @param hub          hub to use
     */
    public PlaceCommand(InputHandler inputHandler, Hex hex, Hub hub) {
        super(COMMAND_NAME, inputHandler, hex, EXPECTED_NUMBER_OF_ARGUMENTS, COMMAND_DESCRIPTION, hub);
    }

    @Override
    protected boolean executeHexCommand(String[] commandArguments) {
        int xCoordinate;
        int yCoordinate;
        // gives error if the arguments aren't a number
        try {
            xCoordinate = Integer.parseInt(commandArguments[X_COORDINATE_INDEX]);
            yCoordinate = Integer.parseInt(commandArguments[Y_COORDINATE_INDEX]);
        } catch (NumberFormatException e) {
            System.err.println(INVALID_COORDINATE_MESSAGE);
            return false;
        }
        // gives error if the coordinates are out of bounds
        if (xCoordinate < 0 || xCoordinate >= hex.getBoard().getSideLength()) {
            System.err.printf(INVALID_X_COORDINATE_MESSAGE, xCoordinate, hex.getBoard().getSideLength() - 1);
            return false;
        }
        if (yCoordinate < 0 || yCoordinate >= hex.getBoard().getSideLength()) {
            System.err.printf(INVALID_Y_COORDINATE_MESSAGE, yCoordinate, hex.getBoard().getSideLength() - 1);
            return false;
        }

        if (hex.isEmpty(xCoordinate, yCoordinate)) {
            hex.set(xCoordinate, yCoordinate);
            return true;
        } else {
            // gives error if the field is already occupied
            System.err.println(OCCUPIED_FIELD_MESSAGE);
            return false;
        }
    }
}
