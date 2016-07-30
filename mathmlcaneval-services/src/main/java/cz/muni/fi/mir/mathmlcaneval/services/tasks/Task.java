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

import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class Task
{
    protected String id;
    protected UserDTO owner;    
    protected TaskStatus taskStatus;    
    protected LocalTime start;
    protected LocalTime end;

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

    public LocalTime getStart()
    {
        return start;
    }

    public void setStart(LocalTime start)
    {
        this.start = start;
    }

    public LocalTime getEnd()
    {
        return end;
    }

    public void setEnd(LocalTime end)
    {
        this.end = end;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Task other = (Task) obj;
        return Objects.equals(this.id, other.id);
    }
}
