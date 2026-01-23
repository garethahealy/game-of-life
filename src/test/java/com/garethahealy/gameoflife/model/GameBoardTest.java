package com.garethahealy.gameoflife.model;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class GameBoardTest {

    @Inject
    GameBoard board;

    @BeforeEach
    void resetBoard() {
        board.reset();
    }

    @Test
    void getCellAt() {
        Cell cell = board.getCellAt(0, 0);

        assertNotNull(cell);
    }

    @Test
    void nextGeneration() {
        setAliveCells(new int[][]{{1, 0}, {1, 1}, {1, 2}});

        board.nextGeneration();

        assertEquals(3, aliveCount());
        assertTrue(board.getCellAt(0, 1).isAlive());
        assertTrue(board.getCellAt(1, 1).isAlive());
        assertTrue(board.getCellAt(2, 1).isAlive());
    }

    @Test
    void reset() {
        setAliveCells(new int[][]{{2, 2}});

        board.reset();

        assertEquals(0, aliveCount());
    }

    private void setAliveCells(int[][] coords) {
        for (int[] coord : coords) {
            board.getCellAt(coord[0], coord[1]).resurrect();
        }

        for (Cell[] row : board.getCells()) {
            for (Cell current : row) {
                current.commitState();
            }
        }
    }

    private long aliveCount() {
        long count = 0;
        for (Cell[] row : board.getCells()) {
            for (Cell cell : row) {
                if (cell.isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }
}
