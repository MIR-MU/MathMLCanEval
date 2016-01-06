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
package cz.muni.fi.mir.mathmlcaneval.api.dto;

import java.io.Serializable;
import java.util.Objects;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ApplicationRunDTO implements Serializable
{
    private static final long serialVersionUID = -3367780482829168589L;
    private Long id;
    private DateTime startTime;
    private DateTime endTime;
    private String note;
    private GitRevisionDTO gitRevision;
    private ConfigurationDTO configuration;
    private UserDTO user;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(DateTime startTime)
    {
        this.startTime = startTime;
    }

    public DateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(DateTime endTime)
    {
        this.endTime = endTime;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public GitRevisionDTO getGitRevision()
    {
        return gitRevision;
    }

    public void setGitRevision(GitRevisionDTO gitRevision)
    {
        this.gitRevision = gitRevision;
    }

    public ConfigurationDTO getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(ConfigurationDTO configuration)
    {
        this.configuration = configuration;
    }

    public UserDTO getUser()
    {
        return user;
    }

    public void setUser(UserDTO user)
    {
        this.user = user;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final ApplicationRunDTO other = (ApplicationRunDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "ApplicationRunDTO{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", note=" + note + 
                ", gitRevision=" + gitRevision.getId() + ", configuration=" + configuration.getId() + ", canonicOutputs=" + 
               ", user=" + user.getId() + '}';
    }
    
    
}
