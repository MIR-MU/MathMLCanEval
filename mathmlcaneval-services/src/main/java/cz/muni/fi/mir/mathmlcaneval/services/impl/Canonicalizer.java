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
package cz.muni.fi.mir.mathmlcaneval.services.impl;

import cz.muni.fi.mir.mathmlcaneval.api.dto.ConfigurationDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.FormulaDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitRevisionDTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class Canonicalizer
{
    private static final Logger LOGGER = LogManager.getLogger(Canonicalizer.class);
    private Path canonicalizerPath;
    private Object canonicalizer;
    private Method executableMethod;
    private GitRevisionDTO gitRevisionDTO;
    private ConfigurationDTO gitConfigurationDTO;
    private String name;
    private int warmupCount;

    private static final String WARMUP_FORMULA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">\n"
            + "  <msqrt>\n"
            + "    <mo>-</mo>\n"
            + "    <mn>1</mn>\n"
            + "  </msqrt>\n"
            + "</math>";

    public Path getCanonicalizerPath()
    {
        return canonicalizerPath;
    }

    public void setCanonicalizerPath(Path canonicalizerPath)
    {
        this.canonicalizerPath = canonicalizerPath;
    }

    public int getWarmupCount()
    {
        return warmupCount;
    }

    public void setWarmupCount(int warmupCount)
    {
        this.warmupCount = warmupCount;
    }   

    public Object getCanonicalizer()
    {
        return canonicalizer;
    }

    public void setCanonicalizer(Object canonicalizer)
    {
        this.canonicalizer = canonicalizer;
    }

    public Method getExecutableMethod()
    {
        return executableMethod;
    }

    public void setExecutableMethod(Method executableMethod)
    {
        this.executableMethod = executableMethod;
    }

    public GitRevisionDTO getGitRevisionDTO()
    {
        return gitRevisionDTO;
    }

    public void setGitRevisionDTO(GitRevisionDTO gitRevisionDTO)
    {
        this.gitRevisionDTO = gitRevisionDTO;
    }

    public ConfigurationDTO getGitConfigurationDTO()
    {
        return gitConfigurationDTO;
    }

    public void setGitConfigurationDTO(ConfigurationDTO gitConfigurationDTO)
    {
        this.gitConfigurationDTO = gitConfigurationDTO;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String canonicalize(FormulaDTO formula)
    {
        return invokeCanonicalizeMethod(name);
    }

    public void warmUp()
    {
        for(int i = 0 ; i < warmupCount; i++)
        {
            invokeCanonicalizeMethod(WARMUP_FORMULA);
        }
    }

    private String invokeCanonicalizeMethod(String input)
    {
        // TODO: improve to make it reusable ? so object is not always created again
        try(InputStream is = new ByteArrayInputStream(input.getBytes()); OutputStream os = new ByteArrayOutputStream())
        {
            executableMethod.invoke(canonicalizer, is, os);
            
            return os.toString();
        }
        catch (IOException | InvocationTargetException | IllegalAccessException | IllegalArgumentException ex)
        {
            LOGGER.fatal(ex);
        }
        
        return null;
    }

}
