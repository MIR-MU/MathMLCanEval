package cz.muni.fi.mir.scheduling;

import java.util.concurrent.Callable;

public abstract class ApplicationTask implements Callable<TaskStatus>
{
    private TaskStatus status;

    public TaskStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(TaskStatus status)
    {
        this.status = status;
    }
}
