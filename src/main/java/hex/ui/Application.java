package hex.ui;

import hex.model.Hub;
import hex.model.entity.HumanPlayer;
import hex.model.entity.Player;
import hex.model.Entry;
import hex.model.entity.artificialplayers.BogoAI;
import hex.model.entity.artificialplayers.HeroAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main entry class to run application.
 *
 * @author unxgx
 */
public final class Application {

    private static final String ERROR_UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    private static final String ERROR_ILLEGAL_COMMAND_ARGUMENTS = "Error: Illegal command arguments.";
    private static final String ERROR_EQUAL_PLAYER_NAMES = "Error: Player names must be different.";
    private static final String ERROR_AI_PLAYER_ONE = "Error: AI can't be player one.";
    private static final String ARGUMENT_AUTO_PRINT = "auto-print";
    private static final int ODD_NUMBERS_MODULO = 2;
    private static final int ARGS_MIN_COUNT = 3;
    private static final int ARGS_MAX_COUNT = 4;
    private static final int INDEX_ARG_SIDE_LENGTH = 0;
    private static final int SIDE_LENGTH_MIN = 5;
    private static final int SIDE_LENGTH_MAX = 12345;
    private static final int INDEX_ARG_PLAYER_ONE_NAME = 1;
    private static final int INDEX_ARG_PLAYER_TWO_NAME = 2;
    private static final int INDEX_ARG_AUTO_PRINT = 3;
    private static final List<String> ARTIFICIAL_PLAYERS = new ArrayList<>(Arrays.asList("BogoAI", "HeroAI"));
    private static final int BOGO_AI_INDEX = 0;
    private static final int HERO_AI_INDEX = 1;


    private Application() {
        throw new IllegalStateException(ERROR_UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Main method used as entry point.
     *
     * @param args
     */
    public static void main(String[] args) {

        int sideLength;
        Player playerOne;
        Player playerTwo;
        boolean autoPrint = false;

        if (args.length != ARGS_MIN_COUNT && args.length != ARGS_MAX_COUNT) {
            System.err.println(ERROR_ILLEGAL_COMMAND_ARGUMENTS);
            return;
        }
        boolean useAI = ARTIFICIAL_PLAYERS.contains(args[INDEX_ARG_PLAYER_TWO_NAME]);
        try {
            sideLength = getSideLength(args[INDEX_ARG_SIDE_LENGTH]);
            playerOne = getPlayerOne(args[INDEX_ARG_PLAYER_ONE_NAME]);
            playerTwo = getPlayerTwo(args[INDEX_ARG_PLAYER_TWO_NAME]);
        } catch (InvalidArgsArgumentException e) {
            System.err.println(ERROR_ILLEGAL_COMMAND_ARGUMENTS);
            return;
        }

        if (args.length == ARGS_MAX_COUNT) {
            autoPrint = getAutoPrint(args[INDEX_ARG_AUTO_PRINT]);
        }
        // gives error if player names are equal
        if (args[INDEX_ARG_PLAYER_ONE_NAME].equals(args[INDEX_ARG_PLAYER_TWO_NAME])) {
            System.err.println(ERROR_EQUAL_PLAYER_NAMES);
            return;
        }
        // gives error if player one is a AI
        if (ARTIFICIAL_PLAYERS.contains(args[INDEX_ARG_PLAYER_ONE_NAME])) {
            System.err.println(ERROR_AI_PLAYER_ONE);
            return;
        }

        Hub hub = new Hub(sideLength, playerOne, playerTwo, autoPrint, useAI);
        hub.start();
    }


    private static int getSideLength(String args) throws InvalidArgsArgumentException {
        int sideLength;
        // throws exception if arg is not a number
        try {
            sideLength = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            throw new InvalidArgsArgumentException();
        }
        // throws exception if arg is not odd or not in range
        if (sideLength < SIDE_LENGTH_MIN || sideLength > SIDE_LENGTH_MAX || sideLength % ODD_NUMBERS_MODULO == 0) {
            throw new InvalidArgsArgumentException();
        }

        return sideLength;

    }

    private static Player getPlayerOne(String args) throws InvalidArgsArgumentException {
        if (args.isEmpty()) {
            throw new InvalidArgsArgumentException();
        }
        return new HumanPlayer(args, Entry.X);
    }

    private static Player getPlayerTwo(String args) throws InvalidArgsArgumentException {
        if (args.isEmpty()) {
            throw new InvalidArgsArgumentException();
        }
        if (ARTIFICIAL_PLAYERS.contains(args)) {
            if (args.equals(ARTIFICIAL_PLAYERS.get(BOGO_AI_INDEX))) {
                return new BogoAI(Entry.O);
            } else if (args.equals(ARTIFICIAL_PLAYERS.get(HERO_AI_INDEX))) {
                return new HeroAI(Entry.O);
            }
        }
        return new HumanPlayer(args, Entry.O);
    }

    private static boolean getAutoPrint(String args) throws IllegalArgumentException {
        if (!args.equals(ARGUMENT_AUTO_PRINT)) {
            throw new IllegalArgumentException();
        }
        return true;
    }

}
