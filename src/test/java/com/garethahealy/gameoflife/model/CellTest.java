package com.garethahealy.gameoflife.model;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CellTest {

    @Test
    void defaultsToDeadState() {
        Cell cell = new Cell(3, 4);

        assertTrue(cell.isDead());
        assertFalse(cell.isAlive());
    }

    @Test
    void resurrectAndCommit() {
        Cell cell = new Cell(0, 0, Cell.State.DEAD);

        cell.resurrect();
        cell.commitState();

        assertTrue(cell.isAlive());
    }

    @Test
    void killAndCommit() {
        Cell cell = new Cell(0, 0, Cell.State.ALIVE);

        cell.kill();
        cell.commitState();

        assertTrue(cell.isDead());
    }

    @Test
    void getAdjacentCoordinates() {
        int xCords = 1;
        int yCords = 1;
        Cell cell = new Cell(xCords, yCords);

        assertEquals(8, cell.getAdjacentCoordinates().size());
        assertArrayEquals(new int[]{xCords - 1, yCords - 1}, cell.getAdjacentCoordinates().get(0));
        assertArrayEquals(new int[]{xCords, yCords - 1}, cell.getAdjacentCoordinates().get(1));
        assertArrayEquals(new int[]{xCords + 1, yCords - 1}, cell.getAdjacentCoordinates().get(2));
        assertArrayEquals(new int[]{xCords - 1, yCords}, cell.getAdjacentCoordinates().get(3));
        assertArrayEquals(new int[]{xCords + 1, yCords}, cell.getAdjacentCoordinates().get(4));
        assertArrayEquals(new int[]{xCords - 1, yCords + 1}, cell.getAdjacentCoordinates().get(5));
        assertArrayEquals(new int[]{xCords, yCords + 1}, cell.getAdjacentCoordinates().get(6));
        assertArrayEquals(new int[]{xCords + 1, yCords + 1}, cell.getAdjacentCoordinates().get(7));
    }

    @Test
    void processUnderPopulation() {
        Cell cell = new Cell(0, 0, Cell.State.ALIVE);

        cell.process(1);
        cell.commitState();

        assertTrue(cell.isDead());
    }

    @Test
    void processLiveOn() {
        Cell cell = new Cell(0, 0, Cell.State.ALIVE);

        cell.process(2);
        cell.commitState();
        assertTrue(cell.isAlive());

        cell.process(3);
        cell.commitState();
        assertTrue(cell.isAlive());
    }

    @Test
    void processOvercrowding() {
        Cell cell = new Cell(0, 0, Cell.State.ALIVE);

        cell.process(4);
        cell.commitState();

        assertTrue(cell.isDead());
    }

    @Test
    void processReproduction() {
        Cell cell = new Cell(0, 0, Cell.State.DEAD);

        cell.process(3);
        cell.commitState();

        assertTrue(cell.isAlive());
    }
}
