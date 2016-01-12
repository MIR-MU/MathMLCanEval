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
package cz.muni.fi.mir.mathmlcaneval.services.impl;

import cz.muni.fi.mir.mathmlcaneval.services.FormulaLoaderService;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.Task;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.TaskStatus;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class FormulaLoaderServiceImpl implements FormulaLoaderService
{
    private static final Logger LOGGER = LogManager.getLogger(FormulaLoaderServiceImpl.class);
    
    @Autowired
    private SchedulingTaskExecutor taskExecutor;
    @Autowired
    private BackgroundFormulaConverterFactoryBean backgroundFormulaConverterFactoryBean;    

    @Override
    public void loadInput(Task task) throws IllegalArgumentException
    {
        LOGGER.info("Obtained task.");
        FileVisitor<Path> visitor = new FolderVisitor(task, null);
        LOGGER.info("visitor created");
        //LOGGER.info
        taskExecutor.execute(backgroundFormulaConverterFactoryBean.backgroundFormulaConverter(task));
        LOGGER.info("Task submitted to task executor");
        try
        {
            Files.walkFileTree(task.getSource().getRootPath(), visitor);
        }
        catch (IOException ex)
        {
            LOGGER.error(ex);
        }
        LOGGER.info("Done loading folder");
        task.getFormulaLoadTask().setTaskStatus(TaskStatus.FINISHED);
        LOGGER.info("SuperDone loading folder.");
    }
}
