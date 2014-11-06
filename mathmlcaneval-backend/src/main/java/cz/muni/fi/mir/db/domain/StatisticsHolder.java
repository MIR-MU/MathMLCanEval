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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Entity(name = "statisticsholder")
public class StatisticsHolder implements Serializable
{
    private static final long serialVersionUID = -178615775136815943L;
    
    @Id
    @Column(name = "statisticsholder_id",nullable = false)
    @SequenceGenerator(name="statisticshid_seq", sequenceName="statisticshid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "statisticshid_seq")
    private Long id;
    @Column(name="annotationValue")
    private String annotation;
    @Column(name = "count")
    private Integer count;
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

    public String getAnnotation()
    {
        return annotation;
    }

    public void setAnnotation(String annotation)
    {
        this.annotation = annotation;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
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
        final StatisticsHolder other = (StatisticsHolder) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("StatisticsHolder{id=");
        sb.append(id);
        sb.append(", annotation=");
        sb.append(annotation);
        sb.append(", count=");
        sb.append(count);
        sb.append(", configuration=");
        if(configuration != null)
        {
            sb.append(configuration.getId());
        }
        else
        {
            sb.append(configuration);
        }
        sb.append(", revision=");
        if(revision != null)
        {
            sb.append(revision.getId());
        }
        else
        {
            sb.append(revision);
        }
        
        return sb.append("}").toString();
    }
}
