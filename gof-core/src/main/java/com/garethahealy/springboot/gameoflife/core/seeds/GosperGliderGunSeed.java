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
package com.garethahealy.springboot.gameoflife.core.seeds;

import com.garethahealy.springboot.gameoflife.core.entities.Cell;
import com.garethahealy.springboot.gameoflife.core.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.core.enums.Rules;

public class GosperGliderGunSeed implements Seed {

    @Override
    public Integer[] load(GameBoard board) {
        return new Integer[] {board.getWidth(), board.getHeight()};
    }

    @Override
    public void process(GameBoard board) {
        board.getCellAt(1, 5).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(2, 5).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(1, 6).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(2, 6).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(5, 4).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(5, 5).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(5, 6).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(6, 3).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(6, 7).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(7, 2).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(8, 2).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(7, 8).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(8, 8).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(9, 5).resurrect(Rules.UNDER_POPULATION);

        //Commit
        for (Cell current : board.getCellsCollection()) {
            current.commitState();
        }
    }
}
