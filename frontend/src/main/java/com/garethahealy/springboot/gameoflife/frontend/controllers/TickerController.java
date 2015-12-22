/*
 * #%L
 * GarethHealy :: Game of Life :: Frontend
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
package com.garethahealy.springboot.gameoflife.frontend.controllers;

import com.garethahealy.springboot.gameoflife.backend.services.BoardService;
import com.garethahealy.springboot.gameoflife.backend.services.DefaultBoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TickerController {

    @Autowired
    @Qualifier("drools")
    private BoardService boardService;

    @RequestMapping("/tick")
    public String tick() {
        return boardService.nextGeneration();
    }

}
