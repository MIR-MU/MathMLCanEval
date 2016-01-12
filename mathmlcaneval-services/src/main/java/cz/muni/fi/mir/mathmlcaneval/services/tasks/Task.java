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
package cz.muni.fi.mir.mathmlcaneval.services.tasks;

import cz.muni.fi.mir.mathmlcaneval.api.dto.ConfigurationDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitRevisionDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.SourceDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.UserDTO;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class Task
{
    private Long id;
    private UserDTO owner;
    private TaskOperation taskOperation;
    private TaskStatus taskStatus;
    private FormulaLoadTask formulaLoadTask;
    private ConfigurationDTO configuration;
    private GitRevisionDTO revision;
    private SourceDTO source;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public UserDTO getOwner()
    {
        return owner;
    }

    public void setOwner(UserDTO owner)
    {
        this.owner = owner;
    }

    public TaskOperation getTaskOperation()
    {
        return taskOperation;
    }

    public void setTaskOperation(TaskOperation taskOperation)
    {
        this.taskOperation = taskOperation;
    }

    public TaskStatus getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public FormulaLoadTask getFormulaLoadTask()
    {
        return formulaLoadTask;
    }

    public void setFormulaLoadTask(FormulaLoadTask formulaLoadTask)
    {
        this.formulaLoadTask = formulaLoadTask;
    }

    public ConfigurationDTO getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(ConfigurationDTO configuration)
    {
        this.configuration = configuration;
    }

    public GitRevisionDTO getRevision()
    {
        return revision;
    }

    public void setRevision(GitRevisionDTO revision)
    {
        this.revision = revision;
    }

    public SourceDTO getSource()
    {
        return source;
    }

    public void setSource(SourceDTO source)
    {
        this.source = source;
    }
    
    
}
