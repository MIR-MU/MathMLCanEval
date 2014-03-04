/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.tools.Tools;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * idea taken from
 * http://stackoverflow.com/questions/16104605/dynamically-loading-jar-and-instanciate-an-object-of-a-loaded-class
 * .
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
public class MathCanonicalizerLoaderImpl implements MathCanonicalizerLoader
{

    static final Logger logger = Logger.getLogger(MathCanonicalizerLoaderImpl.class);
    private String mainClass;
    private String tempFolder;
    private Path path;

    /**
     * Default and the only protected constructor for this class. To locate .jar
     * file we need following:
     * <ul>
     * <li>path to jar file</li>
     * <li>jarfile prefix</li>
     * <li>version</li>
     * <li>suffix</li>
     * </ul>
     * after we have obtained values we can build path to jar file in following
     * way:
     * <pre>
     * /pathtofile/version/prefix+version+suffix
     * </pre>
     *
     * @param version version of MathML canonicalizer
     * @param path path to the MathML canonicalizer
     * @param jarFileNamePrefix prefix of file
     * @param jarFileNameSuffix suffix of file
     * @param mainClassName name of class in jar file that holds main method
     * which is executed
     * @param tempFolder folder where files are created and deleted after during
     * ApplicationRun. See {@link Files#createTempDirectory(java.lang.String, java.nio.file.attribute.FileAttribute...)
     * } for more information.
     * @throws IllegalArgumentException if any of arguments is empty
     * @throws FileNotFoundException if jarFile on path does not exist
     *
     * @author Dominik Szalai
     * @version 1.0
     * @since 1.0
     */
    private MathCanonicalizerLoaderImpl(String version,
            String path,
            String jarFileNamePrefix,
            String jarFileNameSuffix,
            String mainClassName,
            String tempFolder) throws IllegalArgumentException, FileNotFoundException
    {
        this.mainClass = mainClassName;
        this.path = FileSystems.getDefault().getPath(path,version, jarFileNamePrefix + version + jarFileNameSuffix);
        // TODO co ak neexistuje ?
//        if (!Files.exists(this.path))
//        {
//            throw new FileNotFoundException(this.path.toString());
//        }
        this.tempFolder = tempFolder;
    }

    /**
     * The only method via which we can obtain instance of this class. Same
     * policy applies to this method as on constructor.
     * <ul>
     * <li>path to jar file</li>
     * <li>jarfile prefix</li>
     * <li>version</li>
     * <li>suffix</li>
     * </ul>
     * after we have obtained values we can build path to jar file in following
     * way:
     * <pre>
     * /pathtofile/version/prefix+version+suffix
     * </pre>
     *
     * <b>If any name of argument is changed, or added do not forget to modify
     * applicationContextCore!</b>
     *
     * @param version version of MathML canonicalizer
     * @param path path to the MathML canonicalizer
     * @param jarFileNamePrefix prefix of file
     * @param jarFileNameSuffix suffix of file
     * @param mainClassName name of class in jar file that holds main method
     * which is executed
     * @param tempFolder folder where files are created and deleted after during
     * ApplicationRun. See {@link Files#createTempDirectory(java.lang.String, java.nio.file.attribute.FileAttribute...)
     * } for more information.
     * @return Instance of this class
     * @throws IllegalArgumentException if any of arguments is empty
     * @throws FileNotFoundException if jarFile on path does not exist
     *
     * @author Dominik Szalai
     * @version 1.0
     * @since 1.0
     */
    public static MathCanonicalizerLoaderImpl newInstance(String version,
            String path,
            String jarFileNamePrefix,
            String jarFileNameSuffix,
            String mainClassName,
            String tempFolder) throws IllegalArgumentException, FileNotFoundException
    {
        return new MathCanonicalizerLoaderImpl(version, path, jarFileNamePrefix, jarFileNameSuffix, mainClassName, tempFolder);
    }

    @Override
    public void execute(Formula formula, ApplicationRun applicationRun) throws IOException
    {
        if (path == null)
        {
            throw new RuntimeException();
        }

        URL jarFile = null;
        try
        {
            jarFile = path.toUri().toURL();
        }
        catch (MalformedURLException me)
        {
            logger.fatal(me);
           // throw new IOException(me);
        }

        URLClassLoader cl = URLClassLoader.newInstance(new URL[]
        {
            jarFile
        });

        Class MathMLCanonicalizerCommandLineTool = null;
        try
        {
            MathMLCanonicalizerCommandLineTool = cl.loadClass("cz.muni.fi.mir.mathmlcanonicalization." + mainClass);
        }
        catch (ClassNotFoundException cnfe)
        {
            logger.fatal(cnfe);
        }

        if (MathMLCanonicalizerCommandLineTool != null)
        {
            Method main = null;
            try
            {
                main = MathMLCanonicalizerCommandLineTool.getMethod("main", new Class[]
                {
                    String[].class
                });
            }
            catch (NoSuchMethodException | SecurityException ex)
            {
                logger.fatal(ex);
            }

            if (main != null)
            {
                long start = System.currentTimeMillis();
                Path temp = Files.createTempDirectory(tempFolder);
                Path tempFile = Files.createTempFile(temp, start + "", ".xml");
                Path configFile = Files.createTempFile(temp, "config" + start, ".xml");

                IOUtils.write(formula.getXml(), Files.newOutputStream(tempFile));
                IOUtils.write(applicationRun.getConfiguration().getConfig(), Files.newOutputStream(configFile));

                //TODO zadratovany config -c
                String[] args =
                {
                    "-c",
                    configFile.toString(),
                    tempFile.toString()
                };
                try
                {
                    main.invoke(MathMLCanonicalizerCommandLineTool, new Object[]
                    {
                        args
                    });
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
                {
                    logger.fatal(ex);
                }
                // todo nacitanie a vytovrenie can outputu
                Files.delete(temp);
            }
        }
    }

}
