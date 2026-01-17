package com.garethahealy.gameoflife.model;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(cell.isHit(0, 0));
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

        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }

    private long aliveCount() {
        return board.getCells().stream()
                .filter(Cell::isAlive)
                .count();
    }
}
