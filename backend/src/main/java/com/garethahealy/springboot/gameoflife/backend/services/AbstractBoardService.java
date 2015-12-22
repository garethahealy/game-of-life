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
package com.garethahealy.springboot.gameoflife.backend.services;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.garethahealy.springboot.gameoflife.backend.entities.Cell;
import com.garethahealy.springboot.gameoflife.backend.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.backend.transformers.Transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBoardService implements BoardService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractBoardService.class);

    protected GameBoard board;
    protected Transformer transformer;

    public AbstractBoardService(GameBoard board, Transformer transformer) {
        this.board = board;
        this.transformer = transformer;
    }

    @PostConstruct
    @Override
    public void start() {
        this.board.init();
    }

    @PreDestroy
    @Override
    public void stop() {

    }

    protected abstract void takeTurn(Cell current);

    protected void tick() {
        long startTime = System.nanoTime();

        LOG.info("Ticking...");

        for (Cell current : board.getCells()) {
            takeTurn(current);
        }

        for (Cell current : board.getCells()) {
            current.commitState();
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        LOG.info("Method took: {}", TimeUnit.NANOSECONDS.toMillis(duration));
    }

    public String nextGeneration() {
        tick();

        return transformer.transform(board);
    }

    public String print() {
        return transformer.transform(board);
    }

    public String reset() {
        board.init();

        return transformer.transform(board);
    }
}
