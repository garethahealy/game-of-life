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
package com.garethahealy.springboot.gameoflife.core.services;

import java.util.List;

import com.garethahealy.springboot.gameoflife.core.entities.Cell;
import com.garethahealy.springboot.gameoflife.core.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.core.enums.Rules;
import com.garethahealy.springboot.gameoflife.core.transformers.JsonTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("default")
public class DefaultBoardService extends AbstractBoardService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultBoardService.class);

    public DefaultBoardService() {
        this(48);
    }

    public DefaultBoardService(Integer size) {
        super(new GameBoard(size), new JsonTransformer());
    }

    @Override
    protected void takeTurn(Cell current) {
        Integer aliveNeighbours = 0;

        List<Integer[]> cords = current.getAdjacentCoordinates();
        for (Integer[] xyCords : cords) {
            Integer x = xyCords[0];
            Integer y = xyCords[1];

            Cell found = board.getCellAt(x, y);
            if (found != null && found.isAlive()) {
                aliveNeighbours++;
            }
        }

        LOG.debug("aliveNeighbours: " + aliveNeighbours + " / " + current.toString());

        if (current.isAlive()) {
            if (aliveNeighbours < 2) {
                current.kill(Rules.UNDER_POPULATION);
            } else if (aliveNeighbours.equals(2) || aliveNeighbours.equals(3)) {
                current.resurrect(Rules.LIVE_ON);
            } else if (aliveNeighbours > 3) {
                current.kill(Rules.OVERCROWDING);
            }
        } else if (current.isDead()) {
            if (aliveNeighbours.equals(3)) {
                current.resurrect(Rules.REPRODUCTION);
            }
        }
    }
}
