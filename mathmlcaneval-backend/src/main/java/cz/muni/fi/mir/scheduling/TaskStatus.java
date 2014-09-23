package cz.muni.fi.mir.scheduling;

import org.joda.time.DateTime;

import cz.muni.fi.mir.db.domain.User;

public class TaskStatus
{

    public enum TaskType
    {
        canonicalization ("Canonicalization"),
        massImport ("Mass import");
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
