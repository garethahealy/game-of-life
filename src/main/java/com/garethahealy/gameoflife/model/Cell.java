package com.garethahealy.gameoflife.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Cell {

    public enum State {
        ALIVE, DEAD
    }

    @JsonProperty("xCords")
    private final int xCords;

    @JsonProperty("yCords")
    private final int yCords;

    @JsonIgnore
    private final List<int[]> adjacentCoordinates;

    @JsonProperty("state")
    private State state;

    @JsonIgnore
    private State nextState;

    @JsonIgnore
    private int getXCords() {
        return xCords;
    }

    @JsonIgnore
    public int getYCords() {
        return yCords;
    }

    @JsonIgnore
    public State getState() {
        return state;
    }

    @JsonIgnore
    public List<int[]> getAdjacentCoordinates() {
        return adjacentCoordinates;
    }

    public Cell(int xCords, int yCords) {
        this(xCords, yCords, State.DEAD);
    }

    public Cell(int xCords, int yCords, State state) {
        this.xCords = xCords;
        this.yCords = yCords;
        this.state = state;
        this.nextState = state;
        this.adjacentCoordinates = initAdjacentCoordinates(xCords, yCords);
    }

    private List<int[]> initAdjacentCoordinates(int xCords, int yCords) {
        int[] topLeft = new int[]{xCords - 1, yCords - 1};
        int[] topMiddle = new int[]{xCords, yCords - 1};
        int[] topRight = new int[]{xCords + 1, yCords - 1};

        int[] middleLeft = new int[]{xCords - 1, yCords};
        int[] middleRight = new int[]{xCords + 1, yCords};

        int[] bottomLeft = new int[]{xCords - 1, yCords + 1};
        int[] bottomMiddle = new int[]{xCords, yCords + 1};
        int[] bottomRight = new int[]{xCords + 1, yCords + 1};

        return List.of(topLeft, topMiddle, topRight, middleLeft, middleRight, bottomLeft, bottomMiddle, bottomRight);
    }

    public void process(int aliveNeighbours) {
        if (isAlive()) {
            if (aliveNeighbours < 2) {
                // Rule == UNDER_POPULATION
                kill();
            } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                // Rule == LIVE_ON
                resurrect();
            } else if (aliveNeighbours > 3) {
                // Rule == OVERCROWDING
                kill();
            }
        } else if (isDead()) {
            if (aliveNeighbours == 3) {
                // Rule == REPRODUCTION
                resurrect();
            }
        } else {
            throw new IllegalArgumentException("Should never happen but cell is not alive or dead: " + this);
        }
    }

    @JsonIgnore
    public boolean isAlive() {
        return state == State.ALIVE;
    }

    @JsonIgnore
    public boolean isDead() {
        return state == State.DEAD;
    }

    public void kill() {
        this.nextState = State.DEAD;
    }

    public void resurrect() {
        this.nextState = State.ALIVE;
    }

    public void commitState() {
        this.state = this.nextState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("state", state).append("nextState", nextState)
                .append("xCords", getXCords()).append("yCords", getYCords())
                .append("adjacentCoordinates", adjacentCoordinates).toString();
    }
}
