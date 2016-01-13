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
import cz.muni.fi.mir.mathmlcaneval.services.tasks.FormulaLoadTask;
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
    public void loadInput(FormulaLoadTask formulaLoadTask) throws IllegalArgumentException
    {
        LOGGER.info("Starting FormulaLoader for task {}",formulaLoadTask.getId());
        formulaLoadTask.setTaskStatus(TaskStatus.RUNNING);
        FileVisitor<Path> visitor = new FolderVisitor(formulaLoadTask, formulaLoadTask.getPathMatcherRegex());
        taskExecutor.execute(backgroundFormulaConverterFactoryBean.backgroundFormulaConverter(formulaLoadTask));
        LOGGER.info("Task {} submitted to task executor.",formulaLoadTask.getId());
        try
        {
            Files.walkFileTree(formulaLoadTask.getSource().getRootPath(), visitor);
        }
        catch (IOException ex)
        {
            LOGGER.error(ex);
            formulaLoadTask.setTaskStatus(TaskStatus.INTERRUPTED);
        }
        LOGGER.info("Done loading folder.");
        formulaLoadTask.setTaskStatus(TaskStatus.FINISHED);
        LOGGER.info("FormulaLoder for task {} has finished.",formulaLoadTask.getId());
    }
}
