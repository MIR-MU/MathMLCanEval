/*
 * Copyright 2016 MIR@MU.
 *
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
 */
package cz.muni.fi.mir.mathmlcaneval.webapp.controllers;

import cz.muni.fi.mir.mathmlcaneval.api.dto.GitBranchDTO;
import cz.muni.fi.mir.mathmlcaneval.services.GitServiceIO;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping("/git")
public class GitController
{
    private static final Logger LOGGER = LogManager.getLogger(GitController.class);
    @Autowired
    private GitServiceIO gitServiceIO;

    @RequestMapping("/branches/")
    public ModelAndView branches()
    {
        try
        {
            for (GitBranchDTO gb : gitServiceIO.listBranches())
            {
                LOGGER.info(gb.getName());
            }
        }
        catch (IOException ex)
        {
            LOGGER.fatal(ex);
        }

        return new ModelAndView("redirect:/");

    }
}
