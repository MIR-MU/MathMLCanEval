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
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Queue;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "formulaLoadTask")
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FormulaLoadTask extends Task
{
    private Queue<Path> paths = new LinkedList<>();
    private SourceDTO source;

    public Queue<Path> getPaths()
    {
        return paths;
    }

    public void setPaths(Queue<Path> paths)
    {
        this.paths = paths;
    }

    public SourceDTO getSource()
    {
        return source;
    }

    public void setSource(SourceDTO source)
    {
        this.source = source;
    }

    @Override
    public String toString()
    {
        return "FormulaLoadTask{" + "paths=" + paths + ", source=" + source + '}';
    }
}
