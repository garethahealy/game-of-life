package com.garethahealy.gameoflife.model;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GameBoard {

    private final Integer width;
    private final Integer height;
    private final List<Cell> cells;

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public GameBoard() {
        this(50, 48);
    }

    public GameBoard(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        this.cells = new ArrayList<>();
    }

    @PostConstruct
    void init() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells.add(new Cell(x, y));
            }
        }
    }

    public Cell getCellAt(int xCords, Integer yCords) {
        Cell answer = null;
        for (Cell current : cells) {
            if (current.isHit(xCords, yCords)) {
                answer = current;
                break;
            }
        }

        return answer;
    }

    public List<Cell> nextGeneration() {
        for (Cell current : getCells()) {
            int aliveNeighbours = getAliveNeighbours(current);
            current.process(aliveNeighbours);
        }

        for (Cell current : getCells()) {
            current.commitState();
        }

        return getCells();
    }

    private int getAliveNeighbours(Cell current) {
        int answer = 0;

        List<Integer[]> cords = current.getAdjacentCoordinates();
        for (Integer[] xyCords : cords) {
            Integer x = xyCords[0];
            Integer y = xyCords[1];

            Cell found = getCellAt(x, y);
            if (found != null && found.isAlive()) {
                answer++;
            }
        }

        return answer;
    }

    public List<Cell> reset() {
        for (Cell current : getCells()) {
            current.kill();
        }

        for (Cell current : getCells()) {
            current.commitState();
        }

        return getCells();
    }
}
