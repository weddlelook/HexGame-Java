package hex.model;

import java.util.Set;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * This class represents a hexagonal cell within a Hex game board. It stores information
 * about its position, token (X or O), its neighboring hexagons and whether it is a polar hexagon.
 *
 * @author unxgx
 */
public class Hexagon {
    private final int x;
    private final int y;
    private Entry token;
    private final boolean northPol;
    private final boolean southPol;
    private final boolean eastPol;
    private final boolean westPol;
    private final Set<Hexagon> neighbours;

    /**
     * instantiates a new hexagon.
     *
     * @param xCoord     x coordinate
     * @param yCoord     y coordinate
     * @param sideLength side length of the board
     * @param token      token to be placed on the hexagon
     */
    public Hexagon(int xCoord, int yCoord, int sideLength, Entry token) {
        this.x = xCoord;
        this.y = yCoord;
        northPol = (y == 0);
        southPol = (y == sideLength - 1);
        eastPol = (x == sideLength - 1);
        westPol = (x == 0);

        this.token = token;

        this.neighbours = new LinkedHashSet<>();
        if (token == Entry.X) {
            addNeighbor(x, y + 1, sideLength); // South
            addNeighbor(x, y - 1, sideLength); // North
            addNeighbor(x - 1, y, sideLength); // West
            addNeighbor(x + 1, y, sideLength); // East
            addNeighbor(x + 1, y - 1, sideLength); // Nordeast

            addNeighbor(x - 1, y + 1, sideLength); // Southwest
        } else {
            addNeighbor(x, y - 1, sideLength); // North
            addNeighbor(x, y + 1, sideLength); // South
            addNeighbor(x + 1, y - 1, sideLength); // Nordeast
            addNeighbor(x - 1, y, sideLength); // West
            addNeighbor(x - 1, y + 1, sideLength); // Southwest
            addNeighbor(x + 1, y, sideLength); // East
        }
    }

    private Hexagon(int xCoord, int yCoord, int sideLength) {
        // the neighbours are not needed for the game logic, so not all of their attributes are initialized
        this.x = xCoord;
        this.y = yCoord;

        northPol = (y == 0);
        southPol = (y == sideLength - 1);
        eastPol = (x == sideLength - 1);
        westPol = (x == 0);

        this.neighbours = new LinkedHashSet<>();
    }

    private void addNeighbor(int neighborX, int neighborY, int sideLength) {
        // Adds neighbor Hexagon to neighbours list if coordinates are in the limits of the board
        if (neighborX >= 0 && neighborX < sideLength && neighborY >= 0 && neighborY < sideLength) {
            Hexagon neighbor = new Hexagon(neighborX, neighborY, sideLength);
            neighbours.add(neighbor);
            neighbor.neighbours.add(this);
        }
    }

    /**
     * Returns the unmodifiable set of neighboring hexagons.
     *
     * @return the unmodifiable set of neighboring hexagons
     */
    public Set<Hexagon> getNeighbours() {
        return Collections.unmodifiableSet(this.neighbours);
    }

    /**
     * returns wheter the hexagon is a north hexagon.
     *
     * @return true if the hexagon is a north hexagon, false otherwise
     */
    public boolean isNorthPol() {
        return this.northPol;
    }

    /**
     * returns wheter the hexagon is a south hexagon.
     *
     * @return true if the hexagon is a south hexagon, false otherwise
     */
    public boolean isSouthPol() {
        return this.southPol;
    }

    /**
     * returns wheter the hexagon is a east hexagon.
     *
     * @return true if the hexagon is a east hexagon, false otherwise
     */
    public boolean isEastPol() {
        return this.eastPol;
    }

    /**
     * returns wheter the hexagon is a west hexagon.
     *
     * @return true if the hexagon is a west hexagon, false otherwise
     */
    public boolean isWestPol() {
        return this.westPol;
    }

    /**
     * returns the token of the hexagon.
     *
     * @return the token of the hexagon
     */
    public Entry getToken() {
        return this.token;
    }

    /**
     * returns the x coordinate of the hexagon.
     *
     * @return the x coordinate of the hexagon
     */
    public int getX() {
        return this.x;
    }

    /**
     * returns the y coordinate of the hexagon.
     *
     * @return returns the y coordinate of the hexagon
     */
    public int getY() {
        return this.y;
    }

    /**
     * Checks if this hexagon is equal to another object based on its coordinates.
     *
     * @param obj the object to compare this hexagon to
     * @return true if the hexagons are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Hexagon hexagon = (Hexagon) obj;
        return x == hexagon.x
                && y == hexagon.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
