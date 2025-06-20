package com.garethahealy.gameoflife.seeds;

import com.garethahealy.gameoflife.model.GameBoard;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CsvLoaderSeedTest {

    @Inject
    GameBoard board;

    @Test
    void process() throws IOException {
        CsvLoaderSeed seed = new CsvLoaderSeed("src/main/resources/CsvLoaderSeed.csv");
        seed.process(board);

        assertTrue(board.getCellAt(0, 0).isDead());
        assertTrue(board.getCellAt(13, 5).isAlive());
    }
}
