package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.GameBoard;
import com.garethahealy.gameoflife.seeds.impl.CsvLoaderSeed;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CsvLoaderSeedTest {

    @Inject
    GameBoard board;

    @BeforeEach
    void resetBoard() {
        board.reset();
    }

    @Test
    void process() throws IOException {
        CsvLoaderSeed seed = new CsvLoaderSeed("CsvLoaderSeed.csv");
        new PatternApplier().applyAndCommit(board, seed.process());

        assertTrue(board.getCellAt(0, 0).isDead());
        assertTrue(board.getCellAt(13, 5).isAlive());
    }
}
