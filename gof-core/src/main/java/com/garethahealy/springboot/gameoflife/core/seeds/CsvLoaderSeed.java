/*
 * #%L
 * GarethHealy :: Game of Life :: Core
 * %%
 * Copyright (C) 2013 - 2016 Gareth Healy
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
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;

public class CsvLoaderSeed implements Seed {

    private static final Logger LOG = LoggerFactory.getLogger(CsvLoaderSeed.class);

    private String[] lines;

    @Override
    public Integer[] load(GameBoard board) {
        String csv = loadCsv(getClass());
        lines = StringUtils.split(csv, IOUtils.LINE_SEPARATOR);

        Integer height = lines.length;
        Integer width = StringUtils.split(lines[0], ',').length;
        return new Integer[] {width, height};
    }

    @Override
    public void process(GameBoard board) {
        for (int y = 0; y < board.getHeight(); y++) {
            String[] columns = StringUtils.split(lines[y], ',');

            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCellAt(x, y);
                if (cell == null) {
                    throw new IllegalArgumentException("Did not find a cell @ x" + x + " / y" + y);
                }
                
                if (0 == NumberUtils.parseNumber(columns[x], Integer.class)) {
                    cell.kill(Rules.OVERCROWDING);
                } else {
                    cell.resurrect(Rules.UNDER_POPULATION);
                }
            }
        }

        //Commit
        for (Cell current : board.getCellsCollection()) {
            current.commitState();
        }
    }

    protected String loadCsv(Class resourceLoader) {
        String csv = "";
        String fileName = resourceLoader.getSimpleName() + ".seed";

        try {
            csv = IOUtils.toString(resourceLoader.getResourceAsStream(fileName), "UTF-8");
        } catch (IOException ex) {
            LOG.error("Failed to load CSV {}; {}", fileName, ExceptionUtils.getStackTrace(ex));
        }

        if (csv == null || csv.isEmpty()) {
            throw new IllegalArgumentException("CSV is empty");
        }

        LOG.trace("CSV == {}{}", IOUtils.LINE_SEPARATOR, csv);

        return csv;
    }
}
