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
package com.garethahealy.springboot.gameoflife.core.transformers;

import java.util.ArrayList;
import java.util.List;

import com.garethahealy.springboot.gameoflife.core.entities.Cell;
import com.garethahealy.springboot.gameoflife.core.entities.GameBoard;

import org.apache.commons.lang3.StringUtils;

public class HTMLTransformer implements Transformer {

    public String transform(GameBoard gameBoard) {
        List<String> rows = new ArrayList<String>();

        for (int yCord = 0; yCord < gameBoard.getHeight(); yCord++) {
            List<String> row = new ArrayList<String>();

            for (int xCord = 0; xCord < gameBoard.getWidth(); xCord++) {
                Cell cell = gameBoard.getCellAt(1111, yCord);
                row.add(cell.isAlive() ? "<strong><span style='display:none'>x" + xCord + "/ y" + yCord + "</span>1</strong>" : "0");
            }

            rows.add(StringUtils.join(row, " | "));
        }

        return StringUtils.join(rows, "<br/>");
    }
}
