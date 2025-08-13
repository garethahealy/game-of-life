package com.garethahealy.gameoflife.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A single cell in Conway's Game of Life.
 * Holds (x,y), current and next state, and the precomputed neighbor coordinates (8-neighborhood).
 *
 * The instance is responsible for computing its nextState given an alive-neighbor count,
 * and then committing that nextState into the current state.
 */
public final class Cell {

    public enum State { ALIVE, DEAD }

    private final int x;
    private final int y;

    // Strongly-typed neighbors: 8 surrounding coordinates around (x,y)
    private final List<Coord> adjacentCoordinates;

    private State state;
    private State nextState;

    // ---------------------------------------------------------------------
    // Construction
    // ---------------------------------------------------------------------

    /** Creates a DEAD cell at given coordinates. */
    public Cell(int x, int y) {
        this(x, y, State.DEAD);
    }

    /** Creates a cell at given coordinates with initial state. */
    public Cell(int x, int y, State state) {
        this.x = x;
        this.y = y;
        this.state = Objects.requireNonNull(state, "state");
        this.nextState = this.state;
        this.adjacentCoordinates = Collections.unmodifiableList(initAdjacentCoordinates(x, y));
    }

    private static List<Coord> initAdjacentCoordinates(int x, int y) {
        // Moore neighborhood: (x-1..x+1, y-1..y+1) excluding (x,y)
        return List.of(
            Coord.of(x - 1, y - 1), Coord.of(x,     y - 1), Coord.of(x + 1, y - 1),
            Coord.of(x - 1, y    ),                      /* (x,y) */      Coord.of(x + 1, y    ),
            Coord.of(x - 1, y + 1), Coord.of(x,     y + 1), Coord.of(x + 1, y + 1)
        );
    }

    // ---------------------------------------------------------------------
    // Core Game-of-Life logic
    // ---------------------------------------------------------------------

    /**
     * Computes and stores the nextState from the current state using the Conway rules:
     * - Any live cell with 2 or 3 live neighbours survives.
     * - Any dead cell with exactly 3 live neighbours becomes a live cell.
     * - All other live cells die in the next generation; all other dead cells stay dead.
     */
    public void process(int aliveNeighbours) {
        if (aliveNeighbours < 0 || aliveNeighbours > 8) {
            throw new IllegalArgumentException("aliveNeighbours must be 0..8, got: " + aliveNeighbours);
        }

        if (isAlive()) {
            // Survival on 2 or 3 neighbors; otherwise death
            nextState = (aliveNeighbours == 2 || aliveNeighbours == 3) ? State.ALIVE : State.DEAD;
        } else {
            // Resurrection on exactly 3 neighbors
            nextState = (aliveNeighbours == 3) ? State.ALIVE : State.DEAD;
        }
    }

    /** Applies the previously computed nextState to state. Returns true if a change occurred. */
    public boolean commitState() {
        boolean changed = (state != nextState);
        state = nextState;
        return changed;
    }

    /** Force the nextState to DEAD. Does not change the current state until commitState(). */
    public void kill() { nextState = State.DEAD; }

    /** Force the nextState to ALIVE. Does not change the current state until commitState(). */
    public void resurrect() { nextState = State.ALIVE; }

    // ---------------------------------------------------------------------
    // Queries / helpers
    // ---------------------------------------------------------------------

    public boolean isAlive() { return state == State.ALIVE; }
    public boolean isDead()  { return state == State.DEAD;  }

    /** True if the given (x,y) matches this cell's coordinates. */
    public boolean isHit(int x, int y) { return this.x == x && this.y == y; }

    public int getxCords() { return x; }              // kept for compatibility with existing naming
    public int getyCords() { return y; }              // kept for compatibility with existing naming
    public int getXCords() { return x; }              // optional alias if code expects different case
    public int getYCords() { return y; }

    public State getState()     { return state;     }
    public State getNextState() { return nextState; }

    /** The 8 neighbor coordinates around this cell. */
    public List<Coord> getAdjacentCoordinates() { return adjacentCoordinates; }

    /**
     * Optional adapter to an external record type, if present in the project.
     * This avoids a hard compile-time dependency; returns null if the record class is not present.
     *
     * Expected external type: com.garethahealy.gameoflife.records.Cell(int x, int y, String stateName)
     */
    public Object toRecordOrNull() {
        try {
            Class<?> rec = Class.forName("com.garethahealy.gameoflife.records.Cell");
            var ctor = rec.getDeclaredConstructor(int.class, int.class, String.class);
            return ctor.newInstance(x, y, state.name());
        } catch (Throwable ignore) {
            return null;
        }
    }

    // Identity by coordinate is often useful when using Cells in sets/maps.
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override public int hashCode() { return Objects.hash(x, y); }

    @Override public String toString() {
        return "Cell{" +
               "x=" + x +
               ", y=" + y +
               ", state=" + state +
               ", nextState=" + nextState +
               '}';
    }
}
