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

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class TaskServiceStatus
{
    private int totalTasks = 0;
    private int finishedTasks = 0;

    public int getTotalTasks()
    {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks)
    {
        this.totalTasks = totalTasks;
    }

    public int getFinishedTasks()
    {
        return finishedTasks;
    }

    public void setFinishedTasks(int finishedTasks)
    {
        this.finishedTasks = finishedTasks;
    }

    @Override
    public String toString()
    {
        return "TaskServiceStatus{" + "totalTasks=" + totalTasks + ", finishedTasks=" + finishedTasks + '}';
    }
    
    
}
