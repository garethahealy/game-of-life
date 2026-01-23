package com.garethahealy.gameoflife.model;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class GameBoard {

    private final int width;
    private final int height;
    private final Cell[][] cellGrid;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        return cellGrid;
    }

    public GameBoard() {
        this(50, 48);
    }

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.cellGrid = new Cell[height][width];
    }

    @PostConstruct
    void init() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y);
                cellGrid[y][x] = cell;
            }
        }
    }

    public Cell getCellAt(int xCords, int yCords) {
        if (xCords < 0 || yCords < 0 || xCords >= width || yCords >= height) {
            return null;
        }

        return cellGrid[yCords][xCords];
    }

    public void nextGeneration() {
        for (Cell[] row : cellGrid) {
            for (Cell current : row) {
                int aliveNeighbours = getAliveNeighbours(current);
                current.process(aliveNeighbours);
            }
        }

        for (Cell[] row : cellGrid) {
            for (Cell current : row) {
                current.commitState();
            }
        }
    }

    private int getAliveNeighbours(Cell current) {
        int answer = 0;

        for (int[] xyCords : current.getAdjacentCoordinates()) {
            int x = xyCords[0];
            int y = xyCords[1];

            Cell found = getCellAt(x, y);
            if (found != null && found.isAlive()) {
                answer++;
            }
        }

        return answer;
    }

    public void reset() {
        for (Cell[] row : cellGrid) {
            for (Cell current : row) {
                current.kill();
                current.commitState();
            }
        }
    }
}
