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
package cz.muni.fi.mir.db.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * This class serves as container for tests. Inside application run store all
 * required values such as additional note, when test started and ended, who run
 * the test, which configuration and revision was used.
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
@Entity(name = "applicationRun")
public class ApplicationRun implements Serializable, Auditable
{

    private static final long serialVersionUID = -6547413491097181885L;

    @Id
    @Column(name = "applicationrun_id",nullable = false)
    @SequenceGenerator(name="applicationrunid_seq", sequenceName="applicationrunid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "applicationrunid_seq")
    private Long id;
    @Column(name = "note")
    private String note;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "startTime")       // sql99
    private DateTime startTime;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "stopTime")
    private DateTime stopTime;
    @OneToOne
    private User user;
    
    @Transient
    private int canonicOutputCount;

    @ManyToOne(fetch = FetchType.EAGER)
    private Configuration configuration;

    @ManyToOne(fetch = FetchType.EAGER)
    private Revision revision;

    @Override
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

    public int getCanonicOutputCount()
    {
        return canonicOutputCount;
    }

    public void setCanonicOutputCount(int canonicOutputCount)
    {
        this.canonicOutputCount = canonicOutputCount;
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
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "ApplicationRun{" + "id=" + id + ", note=" + note + ", startTime=" + startTime + ", stopTime=" + stopTime + ", user=" + user + ", configuration=" + configuration + ", revision=" + revision + '}';
    }
}
