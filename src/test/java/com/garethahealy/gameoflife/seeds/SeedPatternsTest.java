package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.Cell;
import com.garethahealy.gameoflife.model.GameBoard;
import com.garethahealy.gameoflife.seeds.impl.GliderSeed;
import com.garethahealy.gameoflife.seeds.impl.GosperGliderGunSeed;
import com.garethahealy.gameoflife.seeds.impl.HeavyweightSpaceshipSeed;
import com.garethahealy.gameoflife.seeds.impl.ThreeLineSeed;
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
    void gliderSeed() {
        new PatternApplier().applyAndCommit(board, new GliderSeed().process());

        assertEquals(5, aliveCount());
        assertAlive(2, 1);
        assertAlive(3, 2);
        assertAlive(1, 3);
        assertAlive(2, 3);
        assertAlive(3, 3);
    }

    @Test
    void gosperGliderGunSeed() {
        new PatternApplier().applyAndCommit(board, new GosperGliderGunSeed().process());

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
        new PatternApplier().applyAndCommit(board, new HeavyweightSpaceshipSeed().process());

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
    void threeLineSeed() {
        new PatternApplier().applyAndCommit(board, new ThreeLineSeed().process());

        assertEquals(3, aliveCount());
        assertAlive(1, 1);
        assertAlive(2, 1);
        assertAlive(3, 1);
    }

    private void assertAlive(int x, int y) {
        assertTrue(board.getCellAt(x, y).isAlive());
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
