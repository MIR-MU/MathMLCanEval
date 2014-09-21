/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.audit;


import cz.muni.fi.mir.db.domain.User;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Entity(name = "databaseEvent")
public class DatabaseEvent implements Serializable
{
    public enum Operation
    {
        INSERT,
        UPDATE,
        DELETE
    }
    
    @Id
    @Column(name = "databaseevent_id",nullable = false)
    @SequenceGenerator(name="databaseeventid_seq", sequenceName="databaseeventid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "databaseeventid_seq")
    private Long id;
    @Column(name = "dboperation")
    @Enumerated(EnumType.STRING)
    private Operation operation;
    @Column(name = "targetID")
    private Long targetID;
    @Column(name="message")
    private String message;
    @ManyToOne
    private User user;
    @Column(name = "targetClass")
    private String targetClass;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "eventTime")
    private DateTime eventTime; 

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public void setOperation(Operation operation)
    {
        this.operation = operation;
    }

    public Long getTargetID()
    {
        return targetID;
    }

    public void setTargetID(Long targetID)
    {
        this.targetID = targetID;
    }
    
     

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getTargetClass()
    {
        return targetClass;
    }

    public void setTargetClass(String targetClass)
    {
        this.targetClass = targetClass;
    }

    public DateTime getEventTime()
    {
        return eventTime;
    }

    public void setEventTime(DateTime eventTime)
    {
        this.eventTime = eventTime;
    }

    @Override
    public String toString()
    {
        return "DatabaseEvent{" + "id=" + id + ", operation=" + operation + ", targetID=" + targetID + ", message=" + message + ", user=" + user + ", targetClass=" + targetClass + ", eventTime=" + eventTime + '}';
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final DatabaseEvent other = (DatabaseEvent) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}