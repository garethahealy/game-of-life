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
package com.garethahealy.springboot.gameoflife.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.garethahealy.springboot.gameoflife.backend.entities.Cell;
import com.garethahealy.springboot.gameoflife.backend.entities.GameBoard;
import com.garethahealy.springboot.gameoflife.backend.threading.CellCommitStateCallable;
import com.garethahealy.springboot.gameoflife.backend.threading.CellTakeTurnCallable;
import com.garethahealy.springboot.gameoflife.backend.transformers.Transformer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMultiThreadedBoardService extends AbstractBoardService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMultiThreadedBoardService.class);
    private ExecutorService executorService;

    public AbstractMultiThreadedBoardService(GameBoard board, Transformer transformer) {
        super(board, transformer);
    }

    @PostConstruct
    @Override
    public void start() {
        super.start();

        Integer threadCount = board.getSize() / 4;
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @PreDestroy
    @Override
    public void stop() {
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));

            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void tick() {
        long startTime = System.nanoTime();

        LOG.info("Ticking multi threaded...");

        List<Future<Cell>> turnFutures = new ArrayList<Future<Cell>>();
        for (Cell current : board.getCells()) {
            turnFutures.add(executorService.submit(new CellTakeTurnCallable(current) {
                protected Cell processTurn(Cell current) {
                    takeTurn(current);

                    return current;
                }
            }));
        }

        List<Future<Cell>> stateFutures = new ArrayList<Future<Cell>>();
        for (Cell current : board.getCells()) {
            stateFutures.add(executorService.submit(new CellCommitStateCallable(current)));
        }

        waitForFutures(turnFutures);
        waitForFutures(stateFutures);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        LOG.info("Method took: {}", TimeUnit.NANOSECONDS.toMillis(duration));
    }

    private void waitForFutures(List<Future<Cell>> futures) {
        try {
            for (Future<Cell> current : futures) {
                current.get();
            }
        } catch (InterruptedException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));

            Thread.currentThread().interrupt();
        } catch (ExecutionException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
    }
}
