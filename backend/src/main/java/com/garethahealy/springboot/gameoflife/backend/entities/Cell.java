/*
 * #%L
 * backend
 * %%
 * Copyright (C) 2013 - 2015 Gareth Healy
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.garethahealy.springboot.gameoflife.backend.entities;

import java.util.List;

import com.garethahealy.springboot.gameoflife.backend.enums.CellState;
import com.garethahealy.springboot.gameoflife.backend.enums.Rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell {

    private static final Logger LOG = LoggerFactory.getLogger(Cell.class);

    private CellState state = CellState.DEAD;
    private CellState nextState = CellState.DEAD;
    private Integer xCords;
    private Integer yCords;
    private GameBoard board;
    private AdjacentCoordinates adjacentCoordinates;

    public Cell(Integer xCords, Integer yCords, GameBoard board) {
        this.xCords = xCords;
        this.yCords = yCords;
        this.board = board;

        this.adjacentCoordinates = new AdjacentCoordinates(xCords, yCords);
    }

    private String getId() {
        return hashCode() + "; x" + xCords + " / y" + yCords;
    }

    public CellState getState() {
        return state;
    }

    public Integer getyCords() {
        return yCords;
    }

    public Integer getxCords() {
        return xCords;
    }

    public void kill(Rules reason) {
        this.nextState = CellState.DEAD;

        LOG.info("Killing: " + getId());
    }

    public void resurrect(Rules reason) {
        this.nextState = CellState.ALIVE;

        LOG.info("Resurrecting: " + getId());
    }

    public void commitState() {
        this.state = this.nextState;
    }

    public void takeTurn() {
        Integer aliveNeighbours = 0;

        List<Integer[]> cords = this.adjacentCoordinates.getAllCoordinates();
        for (Integer[] xyCords : cords) {
            Integer x = xyCords[0];
            Integer y = xyCords[1];

            Cell found = board.getCellAt(x, y);
            if (found != null && found.getState() == CellState.ALIVE) {
                aliveNeighbours++;
            }
        }

        LOG.info("aliveNeighbours: " + aliveNeighbours + " / " +  getId());

        if (state == CellState.ALIVE) {
            if (aliveNeighbours < 2) {
                LOG.info("aliveNeighbours < 2: " + getId());

                kill(Rules.UNDER_POPULATION);
            } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                LOG.info("aliveNeighbours == 2 || aliveNeighbours == 3: " + getId());

                resurrect(Rules.LIVE_ON);
            } else if (aliveNeighbours > 3) {
                LOG.info("aliveNeighbours > 3: " + getId());

                kill(Rules.OVERCROWDING);
            }
        } else if (state == CellState.ALIVE) {
            if (aliveNeighbours == 3) {
                LOG.info("aliveNeighbours == 3: " + getId());

                resurrect(Rules.REPRODUCTION);
            }
        }
    }
}
