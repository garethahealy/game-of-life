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
package com.garethahealy.springboot.gameoflife.core.seeds;

import java.io.IOException;

import com.garethahealy.springboot.gameoflife.core.entities.Cell;
import com.garethahealy.springboot.gameoflife.core.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.core.enums.Rules;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvLoaderSeed implements Seed {

    private static final Logger LOG = LoggerFactory.getLogger(CsvLoaderSeed.class);

    public void process(GameBoard board) {
        String csv = loadCsv();
        String[] lines = StringUtils.split(csv, IOUtils.LINE_SEPARATOR);

        if (board.getSize() != lines.length) {
            LOG.warn("Board size ({}) != csv lines ({})", board.getSize(), lines.length);
        }

        for (int y = 0; y < board.getSize(); y++) {
            String[] columns = StringUtils.split(lines[y], ',');

            for (int x = 0; x < board.getSize(); x++) {
                Boolean isDead = columns[x].trim().equals("0");

                Cell cell = board.getCellAt(x, y);
                if (cell == null) {
                    throw new IllegalArgumentException("Did not find a cell @ x" + x + " / y" + y);
                }

                if (isDead) {
                    cell.kill(Rules.OVERCROWDING);
                } else {
                    cell.resurrect(Rules.UNDER_POPULATION);
                }

                x++;
            }

            y++;
        }

        //Commit
        for (Cell current : board.getCells()) {
            current.commitState();
        }
    }

    protected String loadCsv() {
        String csv = "";

        try {
            String fileName = CsvLoaderSeed.class.getSimpleName() + ".seed";
            csv = IOUtils.toString(getClass().getResourceAsStream(fileName), "UTF-8");
        } catch (IOException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }

        if (csv == null || csv.isEmpty()) {
            throw new IllegalArgumentException("CSV is empty");
        }

        return csv;
    }
}
