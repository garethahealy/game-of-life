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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garethahealy.springboot.gameoflife.core.entities.Cells;
import com.garethahealy.springboot.gameoflife.core.entities.GameBoard;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonTransformer implements Transformer {

    private static final Logger LOG = LoggerFactory.getLogger(JsonTransformer.class);

    public String transform(GameBoard gameBoard) {
        String json = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(gameBoard.getCells());
        } catch (JsonProcessingException ex) {
            LOG.error("Failed to parse JSON: {}", ExceptionUtils.getStackTrace(ex));
        }

        return json;
    }
}
