/*
 * #%L
 * GarethHealy :: Game of Life :: Core
 * %%
 * Copyright (C) 2013 - 2017 Gareth Healy
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
package com.garethahealy.springboot.gameoflife.core.entities;

import java.util.ArrayList;
import java.util.List;

public class AdjacentCoordinates {

    private Integer xCords;
    private Integer yCords;

    private List<Integer[]> allCoordinates;

    public AdjacentCoordinates(Integer xCords, Integer yCords) {
        this.xCords = xCords;
        this.yCords = yCords;
    }

    public void init() {
        Integer[] topLeft = new Integer[] {xCords - 1, yCords - 1};
        Integer[] topMiddle = new Integer[] {xCords, yCords - 1};
        Integer[] topRight = new Integer[] {xCords + 1, yCords - 1};

        Integer[] middleLeft = new Integer[] {xCords - 1, yCords};
        Integer[] middeRight = new Integer[] {xCords + 1, yCords};

        Integer[] bottomLeft = new Integer[] {xCords - 1, yCords + 1};
        Integer[] bottomMiddle = new Integer[] {xCords, yCords + 1};
        Integer[] bottomRight = new Integer[] {xCords + 1, yCords + 1};

        allCoordinates = new ArrayList<Integer[]>();
        allCoordinates.add(topLeft);
        allCoordinates.add(topMiddle);
        allCoordinates.add(topRight);

        allCoordinates.add(middleLeft);
        allCoordinates.add(middeRight);

        allCoordinates.add(bottomLeft);
        allCoordinates.add(bottomMiddle);
        allCoordinates.add(bottomRight);
    }

    public List<Integer[]> getAllCoordinates() {
        if (allCoordinates == null || allCoordinates.size() != 8) {
            init();
        }

        return allCoordinates;
    }
}
