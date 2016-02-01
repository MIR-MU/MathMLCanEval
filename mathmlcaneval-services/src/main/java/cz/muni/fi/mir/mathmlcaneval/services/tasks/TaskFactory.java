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

import cz.muni.fi.mir.mathmlcaneval.api.dto.SourceDTO;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class TaskFactory
{
    public abstract Task newAbstractTask();
    public abstract FormulaLoadTask abstractFormulaLoadTask();
    public abstract FormulaCanonicalizeTask abstractFormulaCanonicalizeTask();
    public abstract MavenTask abstractMavenTask();
    
    public FormulaLoadTask newFormulaLoadTask(SourceDTO source){
        FormulaLoadTask task = abstractFormulaLoadTask();
        task.setSource(source);
        task.setId(getTaskID());
        
        return task;
    }

    public MavenTask newMavenTask(List<String> goals)
    {
        MavenTask task = abstractMavenTask();
        task.setUserGoals(goals);
        task.setId(getTaskID());
        
        return task;
    }
    
    
    private String getTaskID(){
        return UUID.randomUUID().toString();
    }
}
