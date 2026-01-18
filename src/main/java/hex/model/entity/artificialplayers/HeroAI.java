package hex.model.entity.artificialplayers;

import hex.model.Board;
import hex.model.Hexagon;
import hex.model.entity.ArtificialPlayer;
import hex.model.Entry;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;


/**
 * This class represents the artificial player HeroAI with advanced move generation logic for playing Hex.
 *
 * @author unxgx
 */
public final class HeroAI extends ArtificialPlayer {

    private static final String NAME = "HeroAI";

    /**
     * Instantiates a new HeroAI with the specified token.
     *
     * @param token the token of the AI
     */
    public HeroAI(Entry token) {
        super(NAME, token);
    }

    @Override
    public String getMove() {
        Board board = currentHex.getBoard();
        int currentTurn = currentHex.getCurrentTurn();
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
        // gets the next move
        int[] nextMoveCoordinates = nextMoveRecursively(currentTurn, board, 1, new HashSet<>());
        int xCoordinate = nextMoveCoordinates[0];
        int yCoordinate = nextMoveCoordinates[1];
        System.out.printf(PLACE_SUCCESSFUL, NAME, xCoordinate, yCoordinate);
        return String.format(PLACE_COMMAND, xCoordinate, yCoordinate);

    }

    private int[] nextMoveRecursively(int currentTurn, Board board, int rollingBack, Set<Hexagon> rollbacked) {
        // calculates the first move if there is no token on the board or accepted so
        if (!board.containsAny(getToken())) {
            int[] firstMove = firstMoveCalculator(board, rollbacked);
            return firstMove;
        }
        // getting the last placed token, with every rollback the last placed token is getting older
        String lastMoveHistory = currentHex.getHistory().get(currentTurn - 2 * rollingBack);
        String[] parts = lastMoveHistory.split(":");
        String lastMove = parts[1].trim();
        String[] lastMoveString = lastMove.split(" ");
        int[] lastMoveCoordinates = new int[lastMoveString.length];
        for (int i = 0; i < lastMoveString.length; i++) {
            lastMoveCoordinates[i] = Integer.parseInt(lastMoveString[i]);
        }
        Hexagon lastMoveHexagon = board.getEntries()[lastMoveCoordinates[1] * board.getSideLength() + lastMoveCoordinates[0]];
        // if there is a free hexagon next to the last placed token on the shortest path, the AI will place a token there
        List<Hexagon> shortestPath = findShortestPathToEastPol(lastMoveHexagon, board, rollbacked);
        if (shortestPath.size() >= 2) {
            int[] coordinates = {shortestPath.get(1).getX(), shortestPath.get(1).getY()};
            if (!board.isEmpty(coordinates[0], coordinates[1])) {
                board.set(lastMoveCoordinates[0], lastMoveCoordinates[1], null);
                rollbacked.add(lastMoveHexagon);
                return nextMoveRecursively(currentTurn, board, rollingBack + 1, rollbacked);
            }
            return coordinates;
        } else {
            // if the shortest path is blocked, the AI will rollback and try to find another shortest path
            board.set(lastMoveCoordinates[0], lastMoveCoordinates[1], null);
            rollbacked.add(lastMoveHexagon);
            return nextMoveRecursively(currentTurn, board, rollingBack + 1, rollbacked);
        }
    }

    /**
     * This method calculates the first move of the AI. It gives the coordinates of the most west and most north free hexagon.
     *
     * @param board      board
     * @param rollbacked rollbacked hexagons of this player
     * @return the coordinates of the most west and most north free hexagon
     */
    private int[] firstMoveCalculator(Board board, Set<Hexagon> rollbacked) {
        int sideLength = currentHex.getBoard().getSideLength();
        int mostWestX = sideLength;
        int mostNorthY = sideLength;

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                if (board.getEntries()[i * sideLength + j] == null && !rollbacked.contains(board.getEntries()[i * sideLength + j])) {
                    if (j < mostWestX) {
                        mostWestX = j;
                        mostNorthY = i;
                    } else if (j == mostWestX && i < mostNorthY) {
                        mostNorthY = i;
                    }
                }
            }
        }
        return new int[]{mostWestX, mostNorthY};
    }

    /**
     * Finds the shortest path from the given starting hexagon to an east pole on the game board. This method
     * performs Dijkstra's algorithm adjusted to logic of hexagons to find the shortest path while avoiding occupied hexagons and those in
     * the rollbacked set.
     *
     * @param start      The starting hexagon from which the path search begins.
     * @param board      The game board on which the search is performed.
     * @param rollbacked A set containing hexagons that should be excluded from the search.
     * @return A list of hexagons representing the shortest path to the nearest east pole on the board. If no path is found,
     *     an empty list is returned.
     */
    private List<Hexagon> findShortestPathToEastPol(Hexagon start, Board board, Set<Hexagon> rollbacked) {
        Map<Hexagon, Hexagon> previousNodes = new HashMap<>();
        Queue<Hexagon> queue = new LinkedList<>();
        Set<Hexagon> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Hexagon current = queue.poll();

            if (current.isEastPol()) {
                // the shortest path is found
                List<Hexagon> shortestPath = new ArrayList<>();
                Hexagon node = current;
                while (node != null) {
                    shortestPath.add(node);
                    node = previousNodes.get(node);
                }
                Collections.reverse(shortestPath);
                return shortestPath;
            }

            for (Hexagon neighbor : current.getNeighbours()) {
                int x = neighbor.getX();
                int y = neighbor.getY();
                boolean isOccupiedByX = board.isOccupiedBy(x, y, Entry.X);
                if (!visited.contains(neighbor) && !isOccupiedByX) {
                    int sideLength = board.getSideLength();
                    /*
                      it creates a new hexagon with advanced constructor, because the neighbor hexagon was built with a basic constructor
                      and the neighbors of the neighbor hexagon are not initialized.
                     */
                    Hexagon hexagon = new Hexagon(x, y, sideLength, Entry.EMPTY);
                    if (!rollbacked.contains(hexagon)) {
                        queue.add(hexagon);
                        visited.add(hexagon);
                        previousNodes.put(hexagon, current);
                    }
                }
            }

        }
        return new ArrayList<>();
    }
}
