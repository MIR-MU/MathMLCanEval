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

import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class Task
{
    protected String id;
    protected UserDTO owner;    
    protected TaskStatus taskStatus;    
    protected DateTime start;
    protected DateTime end;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public UserDTO getOwner()
    {
        return owner;
    }

    public void setOwner(UserDTO owner)
    {
        this.owner = owner;
    }

    public TaskStatus getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public DateTime getStart()
    {
        return start;
    }

    public void setStart(DateTime start)
    {
        this.start = start;
    }

    public DateTime getEnd()
    {
        return end;
    }

    public void setEnd(DateTime end)
    {
        this.end = end;
    }
}