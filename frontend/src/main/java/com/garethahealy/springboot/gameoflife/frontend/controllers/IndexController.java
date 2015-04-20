/*
 * #%L
 * frontend
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

import com.garethahealy.springboot.gameoflife.backend.services.DefaultBoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private DefaultBoardService boardService;

    @RequestMapping(value = "/")
    public ModelAndView index(ModelMap model) {
        model.addAttribute("message", "Game of Life");
        model.addAttribute("gof", boardService.print());

        return new ModelAndView("index");
    }

    @RequestMapping("/reset")
    public ModelAndView reset(ModelMap model) {
        boardService.reset();

        model.addAttribute("message", "Game of Life");
        model.addAttribute("gof", boardService.print());

        return new ModelAndView("index");
    }
}
