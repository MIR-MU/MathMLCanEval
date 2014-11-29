/* 
 * Copyright 2014 MIR@MU.
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
package cz.muni.fi.mir.mathmlevaluation.services;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Formula;
import cz.muni.fi.mir.mathmlevaluation.scheduling.CanonicalizationTask;
import cz.muni.fi.mir.mathmlevaluation.scheduling.LongRunningTaskFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * idea taken from
 * http://stackoverflow.com/questions/16104605/dynamically-loading-jar-and-instanciate-an-object-of-a-loaded-class
 * .
 *
 * @author siska
 * @version 1.0
 * @since 1.0
 */
@Component("mathCanonicalizerLoader")
public class MathCanonicalizerLoaderImpl implements MathCanonicalizerLoader
{
    static final Logger logger = Logger.getLogger(MathCanonicalizerLoaderImpl.class);
    
    @Value("${mathml-canonicalizer.default.mainclass}")
    private String mainClassName;
    @Value("${mathml-canonicalizer.default.jarFolder}")
    private String jarFolder;

    @Autowired
    private LongRunningTaskFactory taskFactory;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FileService fileService;
    
    private MathCanonicalizerLoaderImpl()
    {
    }

    /**
     * The only method via which we can obtain instance of this class. Same
     * policy applies to this method as on constructor.
     * 
     * To locate .jar file we need following:
     * <ul>
     * <li>folder with jar files built from various commits</li>
     * <li>hash of commit</li>
     * </ul>
     * When the jar of requested commit is not found, checkout
     * the commit, build it and move jar to jarFolder.
     * 
     * <b>If any name of argument is changed, or added do not forget to modify
     * applicationContextCore!</b>
     *    
     *
     * @author siska
     * @version 1.0
     * @return 
     * @since 1.0
     */
    public static MathCanonicalizerLoaderImpl newInstance()
    {
        return new MathCanonicalizerLoaderImpl();
    }

    @Override
    public void execute(List<Formula> formulas, ApplicationRun applicationRun) throws IllegalStateException
    {
        if(!fileService.canonicalizerExists(applicationRun.getRevision().getRevisionHash()))
        {
            throw new IllegalStateException("Given canonicalizer with revision ["+applicationRun.getRevision().getRevisionHash()+"] does not exists.");
        }
        else
        {
            Path path = FileSystems.getDefault().getPath(this.jarFolder, applicationRun.getRevision().getRevisionHash() + ".jar");
            Class mainClass = null;
            URL jarFile = null;
            try
            {
                jarFile = path.toUri().toURL();
            }
            catch (MalformedURLException me)
            {
                logger.fatal(me);
            }
            URLClassLoader cl = URLClassLoader.newInstance(new URL[]
            {
                jarFile
            });

            try
            {
                mainClass = cl.loadClass("cz.muni.fi.mir.mathmlcanonicalization." + this.mainClassName);
            }
            catch (ClassNotFoundException cnfe)
            {
                logger.fatal(cnfe);
            }
            
            CanonicalizationTask ct = taskFactory.createTask();
        
            ct.setDependencies(formulas, applicationRun, mainClass);

            taskService.submitTask(ct);
        }        
    }
}
