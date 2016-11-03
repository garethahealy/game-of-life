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
package com.garethahealy.springboot.gameoflife.core.enums;

public enum Rules {

    //Any live cell with fewer than two live neighbours dies, as if caused by under-population.
    UNDER_POPULATION,

    //Any live cell with two or three live neighbours lives on to the next generation.
    LIVE_ON,

    //Any live cell with more than three live neighbours dies, as if by overcrowding.
    OVERCROWDING,

    //Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
    REPRODUCTION
}
