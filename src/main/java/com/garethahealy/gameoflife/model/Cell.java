package com.garethahealy.gameoflife.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Cell {

    public enum State {
        ALIVE,
        DEAD
    }

    private final int xCords;
    private final int yCords;
    private final List<Integer[]> adjacentCoordinates;

    private State state;
    private State nextState;

    public int getXCords() {
        return xCords;
    }

    public int getYCords() {
        return yCords;
    }

    public List<Integer[]> getAdjacentCoordinates() {
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

    private List<Integer[]> initAdjacentCoordinates(int xCords, int yCords) {
        Integer[] topLeft = new Integer[]{xCords - 1, yCords - 1};
        Integer[] topMiddle = new Integer[]{xCords, yCords - 1};
        Integer[] topRight = new Integer[]{xCords + 1, yCords - 1};

        Integer[] middleLeft = new Integer[]{xCords - 1, yCords};
        Integer[] middleRight = new Integer[]{xCords + 1, yCords};

        Integer[] bottomLeft = new Integer[]{xCords - 1, yCords + 1};
        Integer[] bottomMiddle = new Integer[]{xCords, yCords + 1};
        Integer[] bottomRight = new Integer[]{xCords + 1, yCords + 1};

        return List.of(topLeft, topMiddle, topRight, middleLeft, middleRight, bottomLeft, bottomMiddle, bottomRight);
    }

    public void process(int aliveNeighbours) {
        if (isAlive()) {
            if (aliveNeighbours < 2) {
                //Rule == UNDER_POPULATION
                kill();
            } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                //Rule == LIVE_ON
                resurrect();
            } else if (aliveNeighbours > 3) {
                //Rule == OVERCROWDING
                kill();
            }
        } else if (isDead()) {
            if (aliveNeighbours == 3) {
                //Rule == REPRODUCTION
                resurrect();
            }
        } else {
            throw new IllegalArgumentException("Should never happen but cell is not alive or dead: " + this);
        }
    }

    public boolean isAlive() {
        return state == State.ALIVE;
    }

    public boolean isDead() {
        return state == State.DEAD;
    }

    public boolean isHit(int findXCords, int findYCords) {
        return xCords == findXCords && yCords == findYCords;
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

    public com.garethahealy.gameoflife.records.Cell toRecord() {
        return new com.garethahealy.gameoflife.records.Cell(state, xCords, yCords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("state", state)
                .append("nextState", nextState)
                .append("xCords", xCords)
                .append("yCords", yCords)
                .append("adjacentCoordinates", adjacentCoordinates)
                .toString();
    }
}
