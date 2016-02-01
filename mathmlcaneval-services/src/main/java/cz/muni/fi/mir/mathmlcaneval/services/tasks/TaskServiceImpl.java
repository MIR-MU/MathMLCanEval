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

import cz.muni.fi.mir.mathmlcaneval.api.GitService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import cz.muni.fi.mir.mathmlcaneval.services.FormulaCanonicalizerService;
import cz.muni.fi.mir.mathmlcaneval.services.FormulaLoaderService;
import cz.muni.fi.mir.mathmlcaneval.services.MavenService;
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
    private static final Map<String, TaskWrapper> tasks = new ConcurrentHashMap<>();
    private long lastCheck = System.currentTimeMillis();
    private TaskServiceStatus status = new TaskServiceStatus();
    @Autowired
    private FormulaLoaderService formulaLoaderService;
    @Autowired
    private GitService gitService;
    @Autowired
    private MavenService mavenService;

    @Autowired
    private FormulaCanonicalizerService formulaCanonicalizerService;

    @Async
    @Override
    public Future<Task> submitTask(Task task) throws IllegalArgumentException, InterruptedException
    {
        checkNull(task);
        logger.info("Asynchronous task stared with id {}", task.getId());

        Future<Task> futureResult = new AsyncResult<>(task);

        logger.info("Future created.");

        tasks.put(task.getId(), new TaskWrapper(futureResult, task));
        logger.info("Future added to tasks {}", tasks.size());

        if (task instanceof FormulaLoadTask)
        {
            FormulaLoadTask loadTask = (FormulaLoadTask) task;
            checkLoaderTask(loadTask);
            formulaLoaderService.loadInput(loadTask);
        }
        else if (task instanceof FormulaCanonicalizeTask)
        {

        }
        else if(task instanceof MavenTask)
        {
            mavenService.buildJar((MavenTask) task);
        }
        else
        {
            throw new ClassCastException("uh ");
        }

        return futureResult;
    }

    private void addTaskToTasks(Task task)
    {
        //tasks.put(task.getId(), new TaskWrapper(futureResult, task));
    }

    private void checkNull(Task task) throws IllegalArgumentException
    {
        if (task == null)
        {
            throw new IllegalArgumentException("Given task is null.");
        }
    }

    private void checkLoaderTask(FormulaLoadTask task)
    {
        if (task.getSource() == null)
        {
            throw new IllegalArgumentException("Given task does not have valid source. Its null.");
        }
        if (task.getSource().getId() == null)
        {
            throw new IllegalArgumentException("Given task does not have valid source. Used source does not have valid ID.");
        }
        else
        {
            logger.debug("Task has source with following id {}.",task.getSource().getId());
        }
        if (task.getSource().getRootPath() == null)
        {
            throw new IllegalArgumentException("Given task does not have valid source. Used source does not have valid rootPath.");
        }
        else
        {
            logger.debug("Task has source with following path {}.",task.getSource().getRootPath());
        }
    }

    @Override
    public List<Future<Task>> getFinishedTasks()
    {
        List<Future<Task>> finised = new ArrayList<>();

        for (TaskWrapper tw : tasks.values())
        {
            if (tw.getFuture().isDone())
            {
                finised.add(tw.getFuture());
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
        if (!shouldGenerateNewStatus())
        {
            return status;
        }
        else
        {
            int total = tasks.size();
            int finished = 0;

            for (TaskWrapper tw : tasks.values())
            {
                if (tw.getFuture().isDone())
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
     *
     * @return true if system should generate new taskstatus
     */
    private boolean shouldGenerateNewStatus()
    {
        long difference = 30 * 1000;
        return System.currentTimeMillis() > lastCheck + difference;
    }

    @Override
    public void logState()
    {
        for (String taskId : tasks.keySet())
        {
            TaskWrapper tw = tasks.get(taskId);
            Future<Task> task = tw.getFuture();
            logger.info("Task [{}] isDone? [{}] isCanceled? [{}]", taskId, task.isDone(), task.isCancelled());
        }
    }

    @Override
    public List<Future<Task>> getUserTask(UserDTO user) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
