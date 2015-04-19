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
package com.garethahealy.springboot.gameoflife.backend.seeds;

import java.util.List;

import com.garethahealy.springboot.gameoflife.backend.entities.Cell;
import com.garethahealy.springboot.gameoflife.backend.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.backend.enums.Rules;

public class GosperGliderGunSeed implements Seed {

    public void process(GameBoard board, List<Cell> cells) {
        board.getCellAt(1, 5).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(2, 5).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(1, 6).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(2, 6).resurrect(Rules.UNDER_POPULATION);

        board.getCellAt(5, 4).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(5, 5).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(5, 6).resurrect(Rules.UNDER_POPULATION);

        board.getCellAt(6, 3).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(6, 7).resurrect(Rules.UNDER_POPULATION);

        board.getCellAt(7, 3).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(7, 3).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(8, 8).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(8, 8).resurrect(Rules.UNDER_POPULATION);

        //Commit
        for (Cell current : cells) {
            current.commitState();
        }
    }
}
