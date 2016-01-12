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

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class FormulaLoadTask
{
    private Queue<Path> paths = new LinkedList<>();
    private TaskStatus taskStatus = TaskStatus.NEW;

    public Queue<Path> getPaths()
    {
        return paths;
    }

    public void setPaths(Queue<Path> paths)
    {
        this.paths = paths;
    }

    public TaskStatus getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString()
    {
        return "FormulaLoadTask{" + "paths=" + paths.size() + ", taskStatus=" + taskStatus + '}';
    }

}
