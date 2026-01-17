package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class SeedPatternsTest {

    @Inject
    GameBoard board;

    @BeforeEach
    void resetBoard() {
        board.reset();
    }

    @Test
    void allAliveSeed() {
        new AllAliveSeed().process(board);

        assertEquals(board.getWidth() * board.getHeight(), aliveCount());
    }

    @Test
    void gliderSeed() {
        new GliderSeed().process(board);

        assertEquals(5, aliveCount());
        assertAlive(2, 1);
        assertAlive(3, 2);
        assertAlive(1, 3);
        assertAlive(2, 3);
        assertAlive(3, 3);
    }

    @Test
    void gosperGliderGunSeed() {
        new GosperGliderGunSeed().process(board);

        assertEquals(14, aliveCount());
        assertAlive(1, 5);
        assertAlive(2, 5);
        assertAlive(1, 6);
        assertAlive(2, 6);
        assertAlive(5, 4);
        assertAlive(5, 5);
        assertAlive(5, 6);
        assertAlive(6, 3);
        assertAlive(6, 7);
        assertAlive(7, 2);
        assertAlive(8, 2);
        assertAlive(7, 8);
        assertAlive(8, 8);
        assertAlive(9, 5);
    }

    @Test
    void heavyweightSpaceshipSeed() {
        new HeavyweightSpaceshipSeed().process(board);

        assertEquals(13, aliveCount());
        assertAlive(10, 11);
        assertAlive(13, 10);
        assertAlive(16, 10);
        assertAlive(14, 11);
        assertAlive(16, 11);
        assertAlive(11, 12);
        assertAlive(16, 12);
        assertAlive(10, 13);
        assertAlive(11, 13);
        assertAlive(12, 13);
        assertAlive(13, 13);
        assertAlive(14, 13);
        assertAlive(15, 13);
    }

    @Test
    void squareSeed() {
        new SquareSeed().process(board);

        assertEquals(4, aliveCount());
        assertAlive(1, 1);
        assertAlive(2, 1);
        assertAlive(2, 2);
        assertAlive(1, 2);
    }

    @Test
    void threeLineSeed() {
        new ThreeLineSeed().process(board);

        assertEquals(3, aliveCount());
        assertAlive(1, 1);
        assertAlive(2, 1);
        assertAlive(3, 1);
    }

    private void assertAlive(int x, int y) {
        assertTrue(board.getCellAt(x, y).isAlive());
    }

    private long aliveCount() {
        return board.getCells().stream()
                .filter(Cell::isAlive)
                .count();
    }
}
