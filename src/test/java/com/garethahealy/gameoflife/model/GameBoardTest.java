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
    void getCellAtOutOfBoundsReturnsNull() {
        assertNull(board.getCellAt(-1, 0));
        assertNull(board.getCellAt(0, -1));
        assertNull(board.getCellAt(board.getWidth(), 0));
        assertNull(board.getCellAt(0, board.getHeight()));
    }

    @Test
    void getCellsGridMatchesDimensions() {
        Cell[][] grid = board.getCells();

        assertEquals(board.getHeight(), grid.length);
        assertEquals(board.getWidth(), grid[0].length);
    }

    @Test
    void nextGeneration() {
        Cell cell1 = board.getCellAt(0, 1);
        Cell cell2 = board.getCellAt(1, 1);
        Cell cell3 =  board.getCellAt(2, 1);

        cell1.resurrect();
        cell1.commitState();

        cell2.resurrect();
        cell2.commitState();

        cell3.resurrect();
        cell3.commitState();

        board.nextGeneration();

        assertEquals(3, getAliveCount());
    }

    @Test
    void reset() {
        resurrectAllCells();

        board.reset();

        assertEquals(0, getAliveCount());
    }

    private void resurrectAllCells() {
        for (Cell[] row : board.getCells()) {
            for (Cell current : row) {
                current.resurrect();
                current.commitState();
            }
        }
    }

    private long getAliveCount() {
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
