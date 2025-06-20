package com.garethahealy.gameoflife.model;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class GameBoardTest {

    @Inject
    GameBoard board;

    @Test
    void getCellAt() {
        board.getCellAt(0,0);
    }

    @Test
    void nextGeneration() {
    }

    @Test
    void reset() {
    }
}
