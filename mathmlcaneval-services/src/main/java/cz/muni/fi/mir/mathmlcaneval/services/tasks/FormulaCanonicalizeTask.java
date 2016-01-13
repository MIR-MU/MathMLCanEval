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

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class FormulaCanonicalizeTask extends Task
{
    private ConfigurationDTO configuration;
    private GitRevisionDTO revision;

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

    @Override
    public String toString()
    {
        return "FormulaCanonicalizeTask{" + "configuration=" + configuration + ", revision=" + revision + '}';
    }
}
