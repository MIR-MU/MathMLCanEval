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
package cz.muni.fi.mir.mathmlcaneval.database.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@NamedQueries(
{
    @NamedQuery(name = "Configuration.getAll", query = "SELECT c FROM configuration c ORDER BY c.id DESC"),
    @NamedQuery(name = "Configuration.getAllEnabled", query = "SELECT c FROM configuration c WHERE c.active = true ORDER BY c.id DESC")
})
@Entity(name = "configuration")
public class Configuration implements Serializable
{
    private static final long serialVersionUID = 5678608279618167973L;
    @Id
    @Column(name = "configuration_id", nullable = false)
    @SequenceGenerator(name = "configurationid_seq", sequenceName = "configurationid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "configurationid_seq")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "configuration", length = 10000)
    private String configuration;
    @Column(name = "note", length = 1000)
    private String note;
    @Column(name = "active")
    private Boolean active;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(String configuration)
    {
        this.configuration = configuration;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Configuration other = (Configuration) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Configuration{" + "id=" + id + ", name=" + name + ", configuration=" + configuration + ", note=" + note + ", active=" + active + '}';
    }
}
