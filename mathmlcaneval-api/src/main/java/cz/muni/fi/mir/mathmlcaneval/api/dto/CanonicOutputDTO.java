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
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CanonicOutputDTO implements Serializable
{
    private static final long serialVersionUID = -4779832594830201080L;
    private Long id;
    private String outputContent;
    private String outputHash;
    private Integer runningTime;
    private ApplicationRunDTO applicationRun;
    private List<AnnotationDTO> annotations;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOutputContent()
    {
        return outputContent;
    }

    public void setOutputContent(String outputContent)
    {
        this.outputContent = outputContent;
    }

    public String getOutputHash()
    {
        return outputHash;
    }

    public void setOutputHash(String outputHash)
    {
        this.outputHash = outputHash;
    }

    public Integer getRunningTime()
    {
        return runningTime;
    }

    public void setRunningTime(Integer runningTime)
    {
        this.runningTime = runningTime;
    }

    public ApplicationRunDTO getApplicationRun()
    {
        return applicationRun;
    }

    public void setApplicationRun(ApplicationRunDTO applicationRun)
    {
        this.applicationRun = applicationRun;
    }

    public List<AnnotationDTO> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<AnnotationDTO> annotations)
    {
        this.annotations = annotations;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final CanonicOutputDTO other = (CanonicOutputDTO) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "CanonicOutputDTO{" + "id=" + id + ", outputContent=" + outputContent + ", outputHash=" + outputHash + ", runningTime=" + runningTime + ", applicationRun=" + applicationRun + ", annotations=" + annotations + '}';
    }
}
