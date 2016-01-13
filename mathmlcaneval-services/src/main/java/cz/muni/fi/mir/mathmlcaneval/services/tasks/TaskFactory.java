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
import cz.muni.fi.mir.mathmlcaneval.api.dto.SourceDTO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.GitRevision;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Lookup;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class TaskFactory
{
    private SourceDTO source;
    private GitRevision revision;
    private ConfigurationDTO configurationDTO;
    
    public abstract Task newAbstractTask();
    @Lookup(value = "formulaLoadTask")
    public abstract FormulaLoadTask abstractFormulaLoadTask();
    public abstract FormulaCanonicalizeTask abstractFormulaCanonicalizeTask();
    
    public FormulaLoadTask newFormulaLoadTask(){
        FormulaLoadTask task = abstractFormulaLoadTask();
        task.setSource(source);
        
        
        
        return task;
    }

    public void setSource(SourceDTO source)
    {
        this.source = source;
    }

    public void setRevision(GitRevision revision)
    {
        this.revision = revision;
    }

    public void setConfigurationDTO(ConfigurationDTO configurationDTO)
    {
        this.configurationDTO = configurationDTO;
    }

    
    
    
    private String getTaskID(){
        return UUID.randomUUID().toString();
    }
}
