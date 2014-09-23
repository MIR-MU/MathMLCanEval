package cz.muni.fi.mir.services;

import java.util.List;
import java.util.concurrent.Future;

import cz.muni.fi.mir.scheduling.ApplicationTask;
import cz.muni.fi.mir.scheduling.TaskStatus;

/**
 * Non-persistent service for getting information about long-running tasks
 * like mass import or application runs.
 *
 * TODO: remove finished tasks automatically?
 */
public interface TaskService {

    /**
     * Submit the task to asynchronous task executor and save
     * it along with it's Future result.
     * @param task
     * @return Future reference to task result
     */
    public abstract Future<TaskStatus> submitTask(ApplicationTask task);

    /**
     * Get statuses of all registered tasks.
     * @return
     */
    public abstract List<TaskStatus> getTasks();

}
