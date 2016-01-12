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
import cz.muni.fi.mir.mathmlcaneval.services.tasks.Task;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.TaskService;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.TaskStatus;
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

    @RequestMapping(value = "/new/")
    public ModelAndView submitTasks()
    {
//        for (int i = 0; i < 10; i++)
//        {
            Task task = new Task();
            task.setId(System.currentTimeMillis());
            task.setTaskStatus(TaskStatus.NEW);
            FormulaLoadTask flt = new FormulaLoadTask();
            task.setFormulaLoadTask(flt);
            SourceDTO s = new SourceDTO();
            s.setRootPath(Paths.get("C:\\Users\\emptak\\Desktop\\MathMLCanEval\\testbase"));
            task.setSource(s);

            try
            {
                taskService.submitTask(task);
            }
            catch (IllegalArgumentException | InterruptedException ex)
            {
                LOGGER.error(ex);
            }
//        }

        return new ModelAndView("redirect:/");
    }
    
    
    @RequestMapping(value="/status/")
    public ModelAndView status()
    {
        LOGGER.info(taskService.getStatus());
        taskService.logState();
        
        return new ModelAndView("redirect:/");
    }
}
