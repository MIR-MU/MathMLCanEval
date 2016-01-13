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

import cz.muni.fi.mir.mathmlcaneval.api.dto.SourceDTO;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.FormulaLoadTask;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.TaskFactory;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.TaskService;
import java.nio.file.Paths;
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
@RequestMapping("/tasks")
public class TaskController
{
    private static final Logger LOGGER = LogManager.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskFactory taskFactory;

    @RequestMapping(value = "/new/")
    public ModelAndView submitTasks()
    {
        SourceDTO sourceDTO = new SourceDTO();
        sourceDTO.setId(Long.valueOf("1"));
        sourceDTO.setRootPath(Paths.get("C:\\Users\\emptak\\Desktop\\MathMLCanEval\\testbase"));

        taskFactory.setSource(sourceDTO);

        FormulaLoadTask task = taskFactory.newFormulaLoadTask();

        try
        {
            taskService.submitTask(task);
        }
        catch (IllegalArgumentException | InterruptedException ex)
        {
            LOGGER.error(ex);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/status/")
    public ModelAndView status()
    {
        LOGGER.info(taskService.getStatus());
        taskService.logState();

        return new ModelAndView("redirect:/");
    }
}
