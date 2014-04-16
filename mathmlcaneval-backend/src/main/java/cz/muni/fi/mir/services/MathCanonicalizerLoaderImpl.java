/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.services;

import cz.muni.fi.mir.tasks.CanonicalizationTask;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.tasks.TaskStatus;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

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
    private Class mainClass;
    private Path path;
    private final String repository;
    private final String revision;
    private final String mainClassName;
    private final String jarFolder;

    private AsyncTaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Default and the only protected constructor for this class. 
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
     * @param repository git repository with MathMLCan canonicalizer
     * @param revision hash of commit
     * @param mainClassName name of class in jar file that holds main method.
     * which is executed
     * @param jarFolder the folder where the jar files are located
     * @param tempFolder folder where files are created and deleted after during
     * ApplicationRun. See {@link Files#createTempDirectory(java.lang.String, java.nio.file.attribute.FileAttribute...)
     * } for more information.
     *
     * @author siska
     * @version 1.0
     * @since 1.0
     */
    private MathCanonicalizerLoaderImpl(String repository,
            String revision,
            String mainClassName,
            String jarFolder,
            String tempFolder,
            AsyncTaskExecutor taskExecutor)
    {
        this.taskExecutor = taskExecutor;
        this.repository = repository;
        this.mainClassName = mainClassName;
        this.jarFolder = jarFolder;
        this.revision = revision;
        this.setRevision(revision);

    }

    private String getRevision()
    {
        return this.revision;
    }

    private void setRevision(String revision)
    {
        this.path = FileSystems.getDefault().getPath(this.jarFolder, revision + ".jar");
        if (!Files.exists(this.path))
        {
            logger.fatal("File doesn't exist: " + this.path);
        }

        URL jarFile = null;
        try
        {
            jarFile = this.path.toUri().toURL();
        }
        catch (MalformedURLException me)
        {
            logger.fatal(me);
        }
        URLClassLoader cl = URLClassLoader.newInstance(new URL[]
        {
            jarFile
        });
        this.mainClass = null;
        try
        {
            this.mainClass = cl.loadClass("cz.muni.fi.mir.mathmlcanonicalization." + this.mainClassName);
        }
        catch (ClassNotFoundException cnfe)
        {
            logger.fatal(cnfe);
        }
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
     * @param repository git repository with MathMLCan canonicalizer
     * @param revision hash of default commit
     * @param mainClassName name of class in jar file that holds main method.
     * which is executed
     * @param jarFolder the folder where the jar files are located
     * @param tempFolder folder where files are created and deleted after during
     * ApplicationRun. See {@link Files#createTempDirectory(java.lang.String, java.nio.file.attribute.FileAttribute...)
     * } for more information.
     *
     * @author siska
     * @version 1.0
     * @since 1.0
     */
    public static MathCanonicalizerLoaderImpl newInstance(String repository,
            String revision,
            String mainClassName,
            String jarFolder,
            String tempFolder,
            AsyncTaskExecutor taskExecutor)
    {
        return new MathCanonicalizerLoaderImpl(repository, revision, mainClassName, jarFolder, tempFolder, taskExecutor);
    }

    @Override
    public void execute(Formula formula, ApplicationRun applicationRun)
    {
        this.setRevision(applicationRun.getRevision().getRevisionHash());

        CanonicalizationTask task = new CanonicalizationTask(formula, applicationRun, this.mainClass);
        // inject beans into the task
        applicationContext.getAutowireCapableBeanFactory().autowireBean(task);

        // we need to force-fetch lazy collections. ugly..
        Hibernate.initialize(formula.getOutputs()); 

        Future<TaskStatus> future = taskExecutor.submit(task);
    }
}
