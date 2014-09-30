package cz.muni.fi.mir.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import cz.muni.fi.mir.scheduling.ApplicationTask;
import cz.muni.fi.mir.scheduling.TaskStatus;

@Component("taskService")
public class TaskServiceImpl implements TaskService
{
    @Autowired
    @Qualifier(value = "taskExecutor")
    private AsyncTaskExecutor taskExecutor;

    private final Map<Future<TaskStatus>, ApplicationTask> entries;
    private static final Logger logger = Logger.getLogger(TaskServiceImpl.class);


    public TaskServiceImpl()
    {
        entries = new LinkedHashMap<Future<TaskStatus>, ApplicationTask>();
    }

    @Override
    public Future<TaskStatus> submitTask(ApplicationTask task)
    {
        Future<TaskStatus> future = taskExecutor.submit(task);
        entries.put(future, task);

        return future;
    }

    @Override
    public List<TaskStatus> getTasks()
    {
        List<TaskStatus> statuses = new ArrayList<TaskStatus>();
        for (Future<TaskStatus> future : entries.keySet())
        {
            try
            {
                if (future.isDone())
                {
                    statuses.add(0, future.get());
                } else
                {
                    statuses.add(0, entries.get(future).getStatus());
                }
            } catch (InterruptedException e)
            {
                logger.warn("Task was interrupted.", e);
            } catch (ExecutionException e)
            {
                logger.warn("Task threw exception.", e);
            }
        }
        return statuses;
    }

    @Override
    public void removeFinishedTasks()
    {
        for (Iterator<Map.Entry<Future<TaskStatus>, ApplicationTask>> it = entries.entrySet().iterator(); it.hasNext(); )
        {
              Map.Entry<Future<TaskStatus>, ApplicationTask> entry = it.next();
              if (entry.getKey().isDone() || entry.getKey().isCancelled())
              {
                  it.remove();
              }
        }
    }
}
