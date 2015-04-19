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

import java.util.ArrayList;
import java.util.List;

import com.garethahealy.springboot.gameoflife.backend.enums.CellState;
import com.garethahealy.springboot.gameoflife.backend.seeds.Seed;
import com.garethahealy.springboot.gameoflife.backend.seeds.SeedFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameBoard {

    private static final Logger LOG = LoggerFactory.getLogger(GameBoard.class);

    private Integer size;
    private List<Cell> cells;

    public GameBoard(Integer size) {
        this.size = size;
    }

    public void init() {
        this.cells = new ArrayList<Cell>();

        for (Integer y = 0; y < this.size; y++) {
            for (Integer x = 0; x < this.size; x++) {
                cells.add(new Cell(x, y, this));
            }
        }

        Seed seed = SeedFactory.get("");
        seed.process(this, cells);
    }

    public Cell getCellAt(Integer xCords, Integer yCords) {
        Cell answer = null;
        for (Cell current : cells) {
            if (current.getxCords().equals(xCords) && current.getyCords().equals(yCords)) {
                answer = current;
                break;
            }
        }

        return answer;
    }

    public void tick() {
        LOG.info("Ticking...");

        for (Cell current : cells) {
            current.takeTurn();
        }

        for (Cell current : cells) {
            current.commitState();
        }
    }

    public String print() {
        List<String> rows = new ArrayList<String>();

        for (Integer y = 0; y < this.size; y++) {
            List<String> row = new ArrayList<String>();

            for (Integer x = 0; x < this.size; x++) {
                Cell cell = getCellAt(x, y);
                String representation = cell.getState() == CellState.ALIVE ? "<strong>1</strong>" : "0";

                row.add(representation);
            }

            rows.add(StringUtils.join(row, " | "));
        }

        return StringUtils.join(rows, "<br/>");
    }
}
