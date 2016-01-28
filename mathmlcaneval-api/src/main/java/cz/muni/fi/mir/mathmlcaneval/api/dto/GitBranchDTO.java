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

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class GitBranchDTO implements Serializable
{
    private static final long serialVersionUID = 1411567983243778087L;

    private Long id;
    private String name;
    private boolean active = false;

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

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final GitBranchDTO other = (GitBranchDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "GitBranchDTO{" + "id=" + id + ", name=" + name + ", active=" + active + '}';
    }
}
