/*
 * #%L
 * GarethHealy :: Game of Life :: Backend
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

import com.garethahealy.springboot.gameoflife.backend.entities.Cell;
import com.garethahealy.springboot.gameoflife.backend.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.backend.enums.Rules;

public class ThreeLineSeed implements Seed {

    public void process(GameBoard board) {
        board.getCellAt(1, 1).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(2, 1).resurrect(Rules.UNDER_POPULATION);
        board.getCellAt(3, 1).resurrect(Rules.UNDER_POPULATION);

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }
}
