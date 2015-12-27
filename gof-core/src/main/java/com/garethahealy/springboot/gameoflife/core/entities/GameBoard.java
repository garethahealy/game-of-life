/*
 * #%L
 * GarethHealy :: Game of Life :: Core
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
package com.garethahealy.springboot.gameoflife.core.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.garethahealy.springboot.gameoflife.core.seeds.Seed;
import com.garethahealy.springboot.gameoflife.core.seeds.SeedFactory;

public class GameBoard {

    private Integer size;
    private List<Cell> cells;

    public GameBoard(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    public void init() {
        this.cells = new ArrayList<Cell>();

        for (Integer y = 0; y < this.size; y++) {
            for (Integer x = 0; x < this.size; x++) {
                cells.add(new Cell(x, y, this));
            }
        }

        Seed seed = SeedFactory.get("");
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
