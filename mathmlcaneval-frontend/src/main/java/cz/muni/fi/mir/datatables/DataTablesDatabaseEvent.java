/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.datatables;

import cz.muni.fi.mir.db.interceptors.DatabaseEvent;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class DataTablesDatabaseEvent
{
    private String user;
    private String message;
    private String date;
    private String targetClass;
    private Long targetId;

    public DataTablesDatabaseEvent(DatabaseEvent de)
    {
        if(de.getUser() != null)
        {
            this.user = de.getUser().getUsername();
        }
        this.message = de.getMessage();
        this.date = DateTimeFormat.shortDateTime().print(de.getEventTime());
        this.targetClass = de.getTargetClass();
        this.targetId = de.getTargetID();
    }

    
    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTargetClass()
    {
        return targetClass;
    }

    public void setTargetClass(String targetClass)
    {
        this.targetClass = targetClass;
    }

    public Long getTargetId()
    {
        return targetId;
    }

    public void setTargetId(Long targetId)
    {
        this.targetId = targetId;
    }
    
    
    
    
}
