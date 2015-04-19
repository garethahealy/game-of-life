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

import com.garethahealy.springboot.gameoflife.backend.entities.GameBoard;

import org.springframework.stereotype.Component;

@Component
public class BoardService {

    private GameBoard board;

    public BoardService() {
        this.board = new GameBoard(50);
        this.board.init();
    }

    public String print() {
        return board.print();
    }

    public String nextGeneration() {
        board.tick();

        return board.print();
    }

    public String reset() {
        board.init();

        return board.print();
    }
}