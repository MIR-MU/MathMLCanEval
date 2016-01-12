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
package cz.muni.fi.mir.mathmlcaneval.services.tasks;

import cz.muni.fi.mir.mathmlcaneval.services.FormulaCanonicalizerService;
import cz.muni.fi.mir.mathmlcaneval.services.FormulaLoaderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class TaskServiceImpl implements TaskService
{
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private static final Map<Long,Future<Task>> tasks = new ConcurrentHashMap<>();
    private long lastCheck = System.currentTimeMillis();
    private TaskServiceStatus status = new TaskServiceStatus();
    @Autowired
    private FormulaLoaderService formulaLoaderService;
    
    @Autowired
    private FormulaCanonicalizerService formulaCanonicalizerService;
    
    @Async
    @Override
    public Future<Task> submitTask(Task task) throws IllegalArgumentException, InterruptedException
    {
        logger.info("Asynchronous task stared with id {}",task.getId());
        
        checkTask(task);
        
        Future<Task> futureResult = new AsyncResult<>(task);
        logger.info("Future created.");
        tasks.put(task.getId(), futureResult);
        logger.info("Future added to tasks {}",tasks.size());
//        try
//        {
            formulaLoaderService.loadInput(task);
            //formulaLoaderService.loadInput(Paths.get("C:\\Users\\emptak\\Desktop\\MathMLCanEval\\testbase"), true);
//        switch(task.getTaskOperation()){
//            case CANONICALIZE:
//                //do something
//                break;
//            case IMPORT_DATABASE:
//                //do sth else
//                break;
//            case LOAD_SOURCE:
//                //do sth
//                break;
//        }
//        }
//        catch (IOException ex)
//        {
//            logger.error(ex);
//        }
        
        
        
//        formulaCanonicalizerService.canonicalize(null, null,task);
        
        return futureResult;
    }
    
    
    private void checkTask(Task task) throws IllegalArgumentException
    {
        logger.info("Checking passed task {} for its values.",task.getId());
    }

    @Override
    public List<Future<Task>> getFinishedTasks()
    {
        List<Future<Task>> finised = new ArrayList<>();
        
        for(Future<Task> task : tasks.values())
        {
            if(task.isDone())
            {
                finised.add(task);
            }
        }
        
        return finised;
    }

    @Override
    public void clearFinishedTasks() throws InterruptedException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskServiceStatus getStatus()
    {
        if(!shouldGenerateNewStatus())
        {
            return status;
        }
        else
        {
            int total = tasks.size();
            int finished = 0;
            
            for(Future<Task> task : tasks.values())
            {
                if(task.isDone())
                {
                    finished++;
                }
            }
            
            status = new TaskServiceStatus();
            status.setFinishedTasks(finished);
            status.setTotalTasks(total);
            lastCheck = System.currentTimeMillis();
            
            return status;
        }
    }    
    
    
    /**
     * Separate method allows to change behaviour in later development
     * @return true if system should generate new taskstatus
     */
    private boolean shouldGenerateNewStatus(){
        long difference = 30 * 1000;
        return System.currentTimeMillis() > lastCheck + difference;
    }

    @Override
    public void logState()
    {
        for(Long taskId : tasks.keySet())
        {
            Future<Task> task = tasks.get(taskId);
            logger.info("Task [{}] isDone? [{}] isCanceled? [{}]",taskId,task.isDone(),task.isCancelled());
        }
    }
}
