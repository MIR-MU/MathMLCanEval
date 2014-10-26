/* 
 * Copyright 2014 MIR@MU.
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

    /**
     * Remove all finished (or cancelled) tasks.
     */
    public void removeFinishedTasks();
}
