package com.garethahealy.gameoflife.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class CellTest {

    @Test
    void neighbors_areEight() {
        var c = new Cell(10, 10);
        assertEquals(8, c.getAdjacentCoordinates().size());
        assertTrue(c.getAdjacentCoordinates().contains(Coord.of(9, 9)));
        assertTrue(c.getAdjacentCoordinates().contains(Coord.of(11, 11)));
    }

    @Test
    void conway_rules_survival_birth_death() {
        var live = new Cell(0, 0, Cell.State.ALIVE);

        // Survival with 2 or 3
        live.process(2);
        live.commitState();
        assertEquals(Cell.State.ALIVE, live.getState());

        live.process(3);
        live.commitState();
        assertEquals(Cell.State.ALIVE, live.getState());

        // Death otherwise
        live.process(1);
        live.commitState();
        assertEquals(Cell.State.DEAD, live.getState());

        // Birth on 3 neighbors
        var dead = new Cell(1, 1, Cell.State.DEAD);
        dead.process(3);
        dead.commitState();
        assertEquals(Cell.State.ALIVE, dead.getState());
    }
}
