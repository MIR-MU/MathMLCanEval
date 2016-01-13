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

import cz.muni.fi.mir.mathmlcaneval.api.dto.ApplicationRunDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.CanonicOutputDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.FormulaDTO;
import cz.muni.fi.mir.mathmlcaneval.services.FormulaCanonicalizerService;
import cz.muni.fi.mir.mathmlcaneval.services.tasks.Task;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class FormulaCanonicalizerServiceImpl implements FormulaCanonicalizerService
{
    private static final Logger LOGGER = LogManager.getLogger(FormulaCanonicalizerServiceImpl.class);
    private Map<String, Canonicalizer> canonicalizers = new HashMap<>();
    private String canonicalizersFolder;
    private String mainClassName = "cz.muni.fi.mir.mathmlcanonicalization.MathMLCanonicalizer";

    @PostConstruct
    public void init()
    {
        //check if exists

    }

    @Override
    public void canonicalize(List<FormulaDTO> formulas, ApplicationRunDTO applicationRun, Task task)
    {
        LOGGER.info("canon started");
        Canonicalizer canonicalizer;
        try
        {
            Thread.sleep(2500);
        }
        catch (InterruptedException ex)
        {
            LOGGER.error(ex);
        }

        //get name of canonicalizer
        String canonicalizerName = canonicalizerName(applicationRun);

        // check if canonicalizer exists
        // if not created it and add to "cache"
        if (!canonicalizers.containsKey(canonicalizerName))
        {
            canonicalizer = loadCanonicalizer(applicationRun);
            canonicalizers.put(canonicalizerName, canonicalizer);
        }
        else
        {
            canonicalizer = canonicalizers.get(canonicalizerName);
        }

        //warm up canonicalier. if canonicalizer is not warmed up first executions
        // durate 10 times longer
        canonicalizer.warmUp();

        // prepare output
        List<CanonicOutputDTO> canonicOutputs = new ArrayList<>(formulas.size());

        // for each input formula create canonic output        
        for (FormulaDTO formula : formulas)
        {
            CanonicOutputDTO co = new CanonicOutputDTO();
            long start = System.currentTimeMillis();
            co.setContent(canonicalizer.canonicalize(formula));
            co.setRunningTime((int) (System.currentTimeMillis() - start));
            canonicOutputs.add(co);
        }

    }

    private Canonicalizer loadCanonicalizer(ApplicationRunDTO applicationRun)
    {
        Canonicalizer canonicalizer = new Canonicalizer();
        Path canonicalizerPath = Paths.get(this.canonicalizersFolder, applicationRun.getGitRevision().getRevisionHash() + ".har");
        Class mainClass = null;
        Constructor constructor = null;
        Method executableMethod = null;
        Object canonicalizerObject = null;
        URL jarFile = null;
        try
        {
            jarFile = canonicalizerPath.toUri().toURL();
        }
        catch (MalformedURLException me)
        {
            LOGGER.fatal(me);
        }

        URLClassLoader cl = URLClassLoader.newInstance(new URL[]
        {
            jarFile
        });

        try
        {
            mainClass = cl.loadClass(mainClassName);
        }
        catch (ClassNotFoundException nfe)
        {
            LOGGER.fatal(nfe);
        }

        try
        {
            constructor = mainClass.getConstructor(InputStream.class);
            executableMethod = mainClass.getMethod("canonicalize", InputStream.class, OutputStream.class);
        }
        catch (NoSuchMethodException ex)
        {
            LOGGER.fatal(ex);
        }

        InputStream configuration = new ByteArrayInputStream(applicationRun.getConfiguration().getConfiguration().getBytes());

        try
        {
            canonicalizerObject = constructor.newInstance(configuration);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            LOGGER.fatal(ex);
        }

        canonicalizer.setCanonicalizer(canonicalizerObject);
        canonicalizer.setCanonicalizerPath(canonicalizerPath);
        canonicalizer.setGitConfigurationDTO(applicationRun.getConfiguration());
        canonicalizer.setGitRevisionDTO(applicationRun.getGitRevision());
        canonicalizer.setExecutableMethod(executableMethod);
        canonicalizer.setName(canonicalizerName(applicationRun));
        canonicalizer.setWarmupCount(10);

        return canonicalizer;
    }

    private String canonicalizerName(ApplicationRunDTO applicationRunDTO)
    {
        return applicationRunDTO.getGitRevision().getRevisionHash().substring(0, 5) + "#"
                + applicationRunDTO.getConfiguration().getName();
    }
}
