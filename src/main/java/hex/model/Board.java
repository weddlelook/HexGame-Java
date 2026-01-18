package hex.model;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * This class represents the game board for the game of Hex. It manages the state of the
 * hexagonal cells and provides algorithms for checking winning conditions in the game.
 *
 * @author unxgx
 * @author Programmieren-Team
 */
public class Board {

    private static final String LINE_SEPERATOR = System.lineSeparator();
    private final int sideLength;
    private Hexagon[] entries;
    private Set<Hexagon> winningPath;

    /**
     * Instantiates a new board.
     *
     * @param sideLength sideLength The side length of the game board.
     */
    public Board(int sideLength) {
        this.sideLength = sideLength;
        this.entries = new Hexagon[sideLength * sideLength];
    }

    /**
     * Constructs a new game board by copying the state of an existing board.
     *
     * @param board The existing board to copy.
     */
    private Board(Board board) {
        this(board.sideLength);
        System.arraycopy(board.entries, 0, entries, 0, sideLength * sideLength);
        this.winningPath = board.winningPath;
    }

    /**
     * Gets the index of an empty cell that, when filled with the specified token, would result in a win.
     *
     * @param token The token (X or O) to check for a winning move.
     * @return The index of the winning move, or -1 if no winning move is found.
     */
    public int getWinningIndex(Entry token) {
        for (int i = 0; i < entries.length; i++) {
            if (this.entries[i] != null) {
                continue;
            }
            set(i % sideLength, i / sideLength, token);
            boolean hasWon = hasWon(token);
            entries[i] = null;

            if (hasWon) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if a player with the specified token has won the game by connecting their tokens
     * from one side of the board to the other.
     *
     * @param token The token (X or O) of the player to check for a win.
     * @return True if a player has won, otherwise false.
     */
    public boolean hasWon(Entry token) {
        if (token == Entry.X) {
            return nordToSouthAlgorithm();
        } else if (token == Entry.O) {
            return westToEastAlgorithm();
        }
        return false;
    }

    /**
     * Checks if a hexagon at the specified coordinates is empty.
     *
     * @param col The x-coordinate of the hexagon.
     * @param row The y-coordinate of the hexagon.
     * @return True if the cell is empty, otherwise false.
     */
    public boolean isEmpty(int col, int row) {
        return entries[(row * sideLength) + col] == null;
    }

    /**
     * Checks if a cell at the specified coordinates is occupied by a specific token.
     *
     * @param col   The x-coordinate of the hexagon.
     * @param row   The y-coordinate of the hexagon.
     * @param entry The token (X or O) to check for.
     * @return True if the hexagon cell is occupied by the specified token, otherwise false.
     */
    public boolean isOccupiedBy(int col, int row, Entry entry) {
        if (isEmpty(col, row)) {
            return false;
        } else {
            return entries[(row * sideLength) + col].getToken() == entry;
        }
    }

    /**
     * Checks if the game board contains any hexagons with the specified token.
     *
     * @param entry The token (X or O) to check for.
     * @return True if the board contains hexagon cells with the specified token, otherwise false.
     */
    public boolean containsAny(Entry entry) {
        for (Hexagon hexagon : entries) {
            if (hexagon == null) {
                continue;
            } else if (hexagon.getToken() == entry) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets a hexagon cell at the specified coordinates with the specified token.
     *
     * @param xCoordinate The x-coordinate of the cell.
     * @param yCoordinate The y-coordinate of the cell.
     * @param entry       The token (X or O) to set in the cell.
     */
    public void set(int xCoordinate, int yCoordinate, Entry entry) {
        entries[yCoordinate * sideLength + xCoordinate] = new Hexagon(xCoordinate, yCoordinate, this.sideLength, entry);
    }

    /**
     * Sets the hexagons in the specified set to the special WINNING token.
     *
     * @param checkedHexagons The set of hexagons representing the winning path.
     */
    private void setWinning(Set<Hexagon> checkedHexagons) {
        for (Hexagon hexagon : checkedHexagons) {
            int xCoordinate = hexagon.getX();
            int yCoordinate = hexagon.getY();
            set(xCoordinate, yCoordinate, Entry.WINNING);
        }
    }

    /**
     * Sets the hexagons in the winning path to the specified entry.
     *
     * @param entry The token (X or O) to set for the cells in the winning path.
     */
    public void setWinningPathToBack(Entry entry) {
        for (Hexagon hexagon : this.winningPath) {
            int xCoordinate = hexagon.getX();
            int yCoordinate = hexagon.getY();
            set(xCoordinate, yCoordinate, entry);
        }
    }

    /**
     * Gets the side length of the game board.
     *
     * @return The side length of the game board.
     */
    public int getSideLength() {
        return this.sideLength;
    }

    /**
     * Creates a copy of the game board with the same state.
     *
     * @return A new Board object representing a copy of the current game board.
     */
    public Board copy() {
        return new Board(this);
    }

    /**
     * Checks for a winning path from the north pole to the south pole for the 'X' player.
     *
     * @return True if a winning path exists, otherwise false.
     */
    public boolean nordToSouthAlgorithm() {
        List<Hexagon> hexagonsWithXToken = Arrays.stream(entries)
                .filter(hexagon -> hexagon != null && hexagon.getToken() == Entry.X)
                .toList();

        List<Hexagon> northPolHexagons = hexagonsWithXToken.stream()
                .filter(hexagon -> hexagon.isNorthPol())
                .toList();

        List<Hexagon> southPolHexagons = hexagonsWithXToken.stream()
                .filter(hexagon -> hexagon.isSouthPol())
                .toList();

        if (southPolHexagons.isEmpty() || northPolHexagons.isEmpty()) {
            return false;
        }

        return northPolHexagons.stream()
                .anyMatch(hexagon -> isThereConnection(hexagon, hexagonsWithXToken, new HashSet<>()));
    }

    /**
     * Checks for a winning path from the west pole to the east pole for the 'O' player.
     *
     * @return True if a winning path exists, otherwise false.
     */
    public boolean westToEastAlgorithm() {
        List<Hexagon> hexagonsWithOToken = Arrays.stream(entries)
                .filter(hexagon -> hexagon != null && hexagon.getToken() == Entry.O)
                .toList();

        List<Hexagon> westPolHexagons = hexagonsWithOToken.stream()
                .filter(hexagon -> hexagon.isWestPol())
                .toList();

        List<Hexagon> eastPolHexagons = hexagonsWithOToken.stream()
                .filter(hexagon -> hexagon.isEastPol())
                .toList();

        if (westPolHexagons.isEmpty() || eastPolHexagons.isEmpty()) {
            return false;
        }

        return westPolHexagons.stream()
                .anyMatch(hexagon -> isThereConnection(hexagon, hexagonsWithOToken, new HashSet<>()));
    }

    /**
     * Recursive helper method to check for a connection of tokens on the game board.
     *
     * @param hexagon           The current hexagon being checked for a connection.
     * @param hexagonsWithToken The list of hexagons containing the same token being checked.
     * @param checkedHexagons   A set to keep track of visited hexagons during the recursive search.
     * @return True if a connection of tokens is found, otherwise false.
     */
    private boolean isThereConnection(Hexagon hexagon, List<Hexagon> hexagonsWithToken, Set<Hexagon> checkedHexagons) {

        checkedHexagons.add(hexagon);

        Entry tokenToCheck = hexagon.getToken();
        // end of recursion for X token
        if (tokenToCheck == Entry.X) {
            if (hexagon.isSouthPol()) {
                // adds the neihbours of the polar hexagon to the list of checked hexagons
                for (Hexagon neighbour : hexagon.getNeighbours()) {
                    if (hexagonsWithToken.contains(neighbour)) {
                        checkedHexagons.add(neighbour);
                    }
                }
                this.winningPath = checkedHexagons;
                setWinning(checkedHexagons);
                return true;
            }
            // end of recursion for O token
        } else if (tokenToCheck == Entry.O) {
            if (hexagon.isEastPol()) {
                // adds the neihbours of the polar hexagon to the list of checked hexagons
                for (Hexagon neighbour : hexagon.getNeighbours()) {
                    if (hexagonsWithToken.contains(neighbour)) {
                        checkedHexagons.add(neighbour);
                    }
                }
                this.winningPath = checkedHexagons;
                setWinning(checkedHexagons);
                return true;
            }
        }

        for (Hexagon neighbour : hexagon.getNeighbours()) {
            if (!checkedHexagons.contains(neighbour) && hexagonsWithToken.contains(neighbour)) {
                // it takes the hexagon object, that is placed on the board
                // taking neighbour wouldn't work, because it is initialized with a different constructor and doesn't know its neighbours
                Hexagon nextHexagon = hexagonsWithToken.stream()
                        .filter(obj -> obj.equals(neighbour))
                        .findFirst()
                        .orElse(null);
                return isThereConnection(nextHexagon, hexagonsWithToken, checkedHexagons);
            }
        }

        return false;
    }

    /**
     * Gets an array of hexagons representing the current state of the game board.
     *
     * @return An array of hexagons representing the game board's current state.
     */
    public Hexagon[] getEntries() {
        return this.entries;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        String space = " ";

        for (int row = 0; row < sideLength; row++) {
            stringBuilder.append(space.repeat(row));

            for (int col = 0; col < sideLength; col++) {
                Hexagon hexagon = entries[row * sideLength + col];
                if (hexagon != null) {
                    Entry token = hexagon.getToken();
                    stringBuilder.append(token.getToken());
                } else {
                    stringBuilder.append(Entry.EMPTY.getToken());
                }

                if (col < sideLength - 1) {
                    stringBuilder.append(space);
                }
            }

            stringBuilder.append(LINE_SEPERATOR);
        }

        return stringBuilder.toString();
    }

}

