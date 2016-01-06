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
public class GitRevisionDTO implements Serializable
{
    private static final long serialVersionUID = -2978703782523234778L;

    private Long id;
    private String revisionHash;
    private String note;
    private GitBranchDTO gitBranch;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRevisionHash()
    {
        return revisionHash;
    }

    public void setRevisionHash(String revisionHash)
    {
        this.revisionHash = revisionHash;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public GitBranchDTO getGitBranch()
    {
        return gitBranch;
    }

    public void setGitBranch(GitBranchDTO gitBranch)
    {
        this.gitBranch = gitBranch;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final GitRevisionDTO other = (GitRevisionDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "GitRevisionDTO{" + "id=" + id + ", hash=" + revisionHash + ", note=" + note + ", gitBranch=" + gitBranch + '}';
    }
}
