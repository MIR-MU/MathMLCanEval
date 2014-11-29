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
package cz.muni.fi.mir.mathmlevaluation.db.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * The purpose of this class is to capture Configuration which was used during testing.
 * Because value is stored as Text String with length 10000 so capacity is nearly unlimited.
 * If we are using PostgreSQL database column type will be converted into TEXT.
 * 
 * @author Dominik Szalai
 * 
 * @version 1.0
 * @since 1.0
 */
@Entity(name = "configuration")
public class Configuration implements Serializable, Auditable
{

    private static final long serialVersionUID = -4875490381198661605L;

    @Id
    @Column(name = "configuration_id",nullable = false)
    @SequenceGenerator(name="configurationid_seq", sequenceName="configurationid_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "configurationid_seq")
    private Long id;

    @Column(name = "config",columnDefinition = "text",length = 10000)
    private String config;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Override
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getConfig()
    {
        return config;
    }

    public void setConfig(String config)
    {
        this.config = config;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        return "Configuration{" + "id=" + id + ", name=" + name + ", note=" + note + '}';
    }
}
