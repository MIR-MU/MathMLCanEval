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

import cz.muni.fi.mir.mathmlcaneval.api.FormulaService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.FormulaDTO;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.FormulaLoadTask;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.TaskStatus;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class BackgroundFormulaConverter implements Runnable
{
    private static final Logger LOGGER = LogManager.getLogger(BackgroundFormulaConverter.class);
    private FormulaLoadTask formulaLoadTask;
    @Autowired
    private FormulaService formulaService;
    private long startTime;
    private long timeout = 10000;

    public void setTask(FormulaLoadTask formulaLoadTask)
    {
        this.formulaLoadTask = formulaLoadTask;
    }

    public void setTimeout(long timeout)
    {
        this.timeout = timeout;
    }

    @Override
    public void run()
    {
        startTime = System.currentTimeMillis();
        LOGGER.info("Path to formula process for task {} started.",formulaLoadTask.getId());
        while (true)
        {
            LOGGER.info("Attempting to fill chunks.");
            FormulaDTO[] chunks = new FormulaDTO[50];
            int loadedNumber = 0;

            while (loadedNumber != 50)
            {
                LOGGER.info("Loading chunk");
                Path path = formulaLoadTask.getPaths().poll();
                if (path != null)
                {
                    FormulaDTO f = new FormulaDTO();

                    try
                    {
                        f.setContent(new String(Files.readAllBytes(path), Charset.forName("UTF-8")));
                        f.setPath(path);
                        f.setImportTime(DateTime.now());
                        f.setFormulaHash(DigestUtils.sha1Hex(f.getContent()));
                        LOGGER.debug("Formula at path {} was added to chunks.", path);
                    }
                    catch (IOException ex)
                    {
                        LOGGER.error(ex);
                    }

                    chunks[loadedNumber] = f;
                    loadedNumber++;
                } 

                if (isQueueEmpty() && taskIsFinished())
                {
                    break;
                }
            }

            formulaService.createFormulas(arrayToList(chunks));

            if (isQueueEmpty() && taskIsFinished())
            {
                break;
            }
        }
        LOGGER.info("Path to formula process for task {} ended.",formulaLoadTask.getId());
    }

    /**
     * Method checks whether task queue is empty or not
     *
     * @param task to be checked
     * @return true if task queue is empty, false otherwise
     */
    private boolean isQueueEmpty()
    {
        return formulaLoadTask.getPaths().isEmpty();
    }

    /**
     * Method checks whether subtask for formula loading is finished
     *
     * @param task to be checked
     * @return true if formula load task is finished, false otherwise
     */
    private boolean taskIsFinished()
    {
        return formulaLoadTask.getTaskStatus().equals(TaskStatus.FINISHED);
    }
    
    private <T> List<T> arrayToList(T[] array)
    {
        List<T> result = new ArrayList<>();
        
        for(T t : array)
        {
            if(t!= null)
            {
                result.add(t);
            }
        }
        
        return result;
    }
}
