/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.scheduling.CanonicalizationTask;
import cz.muni.fi.mir.scheduling.LongRunningTaskFactory;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private Class mainClass = null;
    private Path path;
    private final String revision;
    private final String mainClassName;
    private final String jarFolder;

    @Autowired
    @Qualifier(value = "taskExecutor")
    private AsyncTaskExecutor taskExecutor;
    
    @Autowired
    private LongRunningTaskFactory canonicalizationTaskFactory;

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
    private MathCanonicalizerLoaderImpl(String revision,
            String mainClassName,
            String jarFolder)
    {
        this.mainClassName = mainClassName;
        this.jarFolder = jarFolder;
        this.revision = revision;
        this.setRevision(revision);
    }

    private void setRevision(String revision)
    {
        this.path = FileSystems.getDefault().getPath(this.jarFolder, revision + ".jar");
        if (!Files.exists(this.path))
        {
            logger.fatal("File doesn't exist: " + this.path);
            // if folder is not found / or does not have proper access :)
            // we set the one which is defined in pom.xml
            // but somehow does not work
            try
            {
                this.mainClass = this.getClass().getClassLoader().loadClass("cz.muni.fi.mir.mathmlcanonicalization."+this.mainClassName);
            }
            catch(ClassNotFoundException cnfe)
            {
                logger.fatal(cnfe);
            }            
        }
        else
        {
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

            try
            {
                this.mainClass = cl.loadClass("cz.muni.fi.mir.mathmlcanonicalization." + this.mainClassName);
            }
            catch (ClassNotFoundException cnfe)
            {
                logger.fatal(cnfe);
            }
        }
    }
    
    /**
     * Method checks whether file at given path exists. If file does not exist 
     * exception is thrown instead of returning false value. The reason is that 
     * we can later show message with missing path (part of exception message)
     * in page view.
     * @return true if jar file exists
     * @throws FileNotFoundException if file does not exist
     */
    @Override
    public boolean jarFileExists() throws FileNotFoundException
    {
        Path temp = FileSystems.getDefault().getPath(this.jarFolder, revision + ".jar");
        if(Files.exists(temp))
        {
            return true;
        }
        else
        {
            throw new FileNotFoundException("There is no .jar file at defined path: "+temp.toString());
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
     * @param revision hash of default commit
     * @param mainClassName name of class in jar file that holds main method.
     * which is executed
     * @param jarFolder the folder where the jar files are located
     *
     * @author siska
     * @version 1.0
     * @return 
     * @since 1.0
     */
    public static MathCanonicalizerLoaderImpl newInstance(
            String revision,
            String mainClassName,
            String jarFolder)
    {
        return new MathCanonicalizerLoaderImpl(revision, mainClassName, jarFolder);
    }

    @Override
    public void execute(List<Formula> formulas, ApplicationRun applicationRun)
    {
        this.setRevision(applicationRun.getRevision().getRevisionHash());
        CanonicalizationTask ct = canonicalizationTaskFactory.createTask();
        
        ct.setDependencies(formulas, applicationRun, mainClass);
        
        taskExecutor.execute(ct);
    }
}
