/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Empt
 */
@Entity(name = "applicationrun")
public class ApplicationRun implements Serializable
{
    private static final long serialVersionUID = -6547413491097181885L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "note")
    private String note;
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Column(name="startTime")       // sql99
    private DateTime startTime;
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Column(name="stopTime")
    private DateTime stopTime;
    @OneToOne
    private User user;
    
    @ManyToOne
    private Configuration configuration;
    
    @ManyToOne
    private Revision revision;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

    public Revision getRevision()
    {
        return revision;
    }

    public void setRevision(Revision revision)
    {
        this.revision = revision;
    }
    
    

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final ApplicationRun other = (ApplicationRun) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "ApplicationRun{" + "id=" + id + ", note=" + note + ", startTime=" + startTime + ", stopTime=" + stopTime + ", user=" + user + ", configuration=" + configuration + ", revision=" + revision + '}';
    }

    
}
