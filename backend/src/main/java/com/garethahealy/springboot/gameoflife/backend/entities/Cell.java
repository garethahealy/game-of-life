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

import org.apache.commons.lang3.builder.ToStringBuilder;
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

    public List<Integer[]> getAdjacentCoordinates() {
        return adjacentCoordinates.getAllCoordinates();
    }

    public boolean isAlive() {
        return state == CellState.ALIVE;
    }

    public boolean isDead() {
        return state == CellState.DEAD;
    }

    public boolean isHit(Integer findXCords, Integer findYCords) {
        return xCords.equals(findXCords) && yCords.equals(findYCords);
    }

    public void kill(Rules reason) {
        this.nextState = CellState.DEAD;

        LOG.info("Killing: " + reason + " / " + toString());
    }

    public void resurrect(Rules reason) {
        this.nextState = CellState.ALIVE;

        LOG.info("Resurrecting: " + reason + " / " + toString());
    }

    public void commitState() {
        this.state = this.nextState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("xCords", xCords)
            .append("yCords", yCords)
            .append("state", state)
            .append("nextState", nextState)
            .toString();
    }
}
