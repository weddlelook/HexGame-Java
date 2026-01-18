package edu.kit.kastel.hex.model.entity.artificialplayers;

import edu.kit.kastel.hex.model.entity.ArtificialPlayer;
import edu.kit.kastel.hex.model.Entry;
import edu.kit.kastel.hex.model.Board;
import edu.kit.kastel.hex.model.Hexagon;

/**
 * This class represents the artificial player BogoAI with basic move generation logic for playing Hex.
 *
 * @author unxgx
 */
public final class BogoAI extends ArtificialPlayer {

    private static final String NAME = "BogoAI";

    /**
     * Instantiates a new BogoAI with the specified token.
     * @param token the token of the AI
     */
    public BogoAI(Entry token) {
        super(NAME, token);
    }

    @Override
    public String getMove() {

        Board board = currentHex.getBoard();
        Hexagon[] entries = board.getEntries();
        // checks if the AI can win
        int winningIndex = board.getWinningIndex(getToken());
        if (winningIndex != -1) {
            int xCoordinate = winningIndex % board.getSideLength();
            int yCoordinate = winningIndex / board.getSideLength();
            System.out.printf(PLACE_SUCCESSFUL, NAME, xCoordinate, yCoordinate);
            return String.format(PLACE_COMMAND, xCoordinate, yCoordinate);
        }
        // checks if the opponent can win
        Entry opponentToken = getToken() == Entry.X ? Entry.O : Entry.X;
        int preventionIndex = board.getWinningIndex(opponentToken);
        if (preventionIndex != -1) {
            int xCoordinate = preventionIndex % board.getSideLength();
            int yCoordinate = preventionIndex / board.getSideLength();
            System.out.printf(PLACE_SUCCESSFUL, NAME, xCoordinate, yCoordinate);
            return String.format(PLACE_COMMAND, xCoordinate, yCoordinate);
        }
        // gets the last placed token
        int currentTurn = currentHex.getCurrentTurn();
        String lastMoveHistory = currentHex.getHistory().get(currentTurn - 1);
        String[] parts = lastMoveHistory.split(":");
        String lastMove = parts[1].trim();
        String[] lastMoveString = lastMove.split(" ");
        int[] lastMoveCoordinates = new int[lastMoveString.length];
        for (int i = 0; i < lastMoveString.length; i++) {
            lastMoveCoordinates[i] = Integer.parseInt(lastMoveString[i]);
        }
        // swaps the tokens if the last move of player one was on an even sum
        if (currentTurn == 1) {
            int sumLastMove = lastMoveCoordinates[0] + lastMoveCoordinates[1];
            if (sumLastMove % 2 == 0) {
                return SWAP_COMMAND;
            }
        }
        // gets the point symmetry of the last move
        int xCoordinate = pointSymmetryCalculator(lastMoveCoordinates)[0];
        int yCoordinate = pointSymmetryCalculator(lastMoveCoordinates)[1];
        if (board.isEmpty(xCoordinate, yCoordinate)) {
            System.out.printf(PLACE_SUCCESSFUL, NAME, xCoordinate, yCoordinate);
            return String.format(PLACE_COMMAND, xCoordinate, yCoordinate);
        } else {
            // if the point symmetry is already occupied, the AI places the token on the first empty hexagon
            for (int i = 0; i < entries.length; i++) {
                if (entries[i] == null) {
                    xCoordinate = i % board.getSideLength();
                    yCoordinate = i / board.getSideLength();
                    System.out.printf(PLACE_SUCCESSFUL, NAME, xCoordinate, yCoordinate);
                    return String.format(PLACE_COMMAND, xCoordinate, yCoordinate);
                }
            }
        }
        // because there isn't a proven draw on a hex game, this return is never reached
        return null;
    }

    private int[] pointSymmetryCalculator(int[] coordinates) {
        Board board = currentHex.getBoard();
        int centerXCoordinate = board.getSideLength() / 2;
        int centerYCoordinate = board.getSideLength() / 2;

        int[] pointSymmetryCoordinates = new int[2];
        pointSymmetryCoordinates[0] = centerXCoordinate + (centerXCoordinate - coordinates[0]);
        pointSymmetryCoordinates[1] = centerYCoordinate + (centerYCoordinate - coordinates[1]);

        return pointSymmetryCoordinates;

    }

}
