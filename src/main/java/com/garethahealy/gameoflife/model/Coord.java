package com.garethahealy.gameoflife.model;

import java.util.Objects;

/**
 * Simple immutable integer coordinate.
 * Kept minimal so it can be used in collections and as a neighbor type.
 */
public final class Coord {
    public final int x;
    public final int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Convenience factory. */
    public static Coord of(int x, int y) { return new Coord(x, y); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coord)) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override public int hashCode() { return Objects.hash(x, y); }

    @Override public String toString() { return "Coord{" + "x=" + x + ", y=" + y + '}'; }
}
