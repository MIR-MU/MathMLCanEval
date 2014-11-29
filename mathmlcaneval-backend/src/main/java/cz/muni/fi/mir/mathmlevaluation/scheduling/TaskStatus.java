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
package cz.muni.fi.mir.mathmlevaluation.scheduling;

import org.joda.time.DateTime;

import cz.muni.fi.mir.mathmlevaluation.db.domain.User;

/**
 *
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
public class TaskStatus
{
    //enums are constant and should be in upper case :)
    public enum TaskType
    {
        CANONICALIZATION ("Canonicalization"),
        MASSIMPORT ("Mass import");
        private final String name;

        private TaskType(String s)
        {
            name = s;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    private long total;
    private long current;
    private String note;
    private DateTime startTime;
    private DateTime stopTime;
    private User user;
    private TaskType taskType;


    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public long getCurrent()
    {
        return current;
    }

    public void setCurrent(long current)
    {
        this.current = current;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public DateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(DateTime startTime)
    {
        this.startTime = startTime;
    }

    public DateTime getStopTime()
    {
        return stopTime;
    }

    public void setStopTime(DateTime stopTime)
    {
        this.stopTime = stopTime;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public TaskType getTaskType()
    {
        return taskType;
    }

    public void setTaskType(TaskType taskType)
    {
        this.taskType = taskType;
    }
}
