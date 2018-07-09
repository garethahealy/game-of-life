/*
 * #%L
 * GarethHealy :: Game of Life :: Core
 * %%
 * Copyright (C) 2013 - 2018 Gareth Healy
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
package com.garethahealy.springboot.gameoflife.core.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.garethahealy.springboot.gameoflife.core.seeds.Seed;
import com.garethahealy.springboot.gameoflife.core.seeds.SeedFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameBoard {

    private static final Logger LOG = LoggerFactory.getLogger(GameBoard.class);

    private Integer width;
    private Integer height;
    private List<Cell> cells;

    public GameBoard(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Cell> getCellsCollection() {
        return Collections.unmodifiableList(cells);
    }

    public Cells getCells() {
        return new Cells(getCellsCollection(), getWidth(), getHeight());
    }

    public void init() {
        Seed seed = SeedFactory.get("csv");
        Integer[] seedSize = seed.load(this);

        Integer seedWidth = seedSize[0];
        Integer seedHeight = seedSize[1];

        LOG.trace("BoardWidth {} SeedWidth {}", getWidth(), seedWidth);
        if (!getWidth().equals(seedWidth)) {
            LOG.warn("BoardWidth ({}) != SeedWidth ({}). Updating BoardWidth to be same as SeedWidth", getWidth(), seedWidth);
            this.width = seedWidth;
        }

        LOG.trace("BoardHeight {} SeedHeight {}", getHeight(), seedHeight);
        if (!getHeight().equals(seedHeight)) {
            LOG.warn("BoardHeight ({}) != SeedHeight ({}). Updating BoardHeight to be same as SeedHeight", getHeight(), seedHeight);
            this.height = seedHeight;
        }

        this.cells = new ArrayList<Cell>();
        for (Integer y = 0; y < getHeight(); y++) {
            for (Integer x = 0; x < getWidth(); x++) {
                cells.add(new Cell(x, y));
            }
        }

        seed.process(this);
    }

    public Cell getCellAt(Integer xCords, Integer yCords) {
        Cell answer = null;
        for (Cell current : cells) {
            if (current.isHit(xCords, yCords)) {
                answer = current;
                break;
            }
        }

        return answer;
    }
}
