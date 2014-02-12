/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.sandbox;

import cz.muni.fi.mir.tools.Tools;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * taken from 
 * http://stackoverflow.com/questions/16104605/dynamically-loading-jar-and-instanciate-an-object-of-a-loaded-class
 * .
 * @author Empt
 */
public class MathCanonicalizerLoader
{
    private static final String FILENAME = "mathml-canonicalizer-";
    private static final String MAIN_CLASS = "MathMLCanonicalizerCommandLineTool";
    
    private String version;
    private Path path;
    
    
    private MathCanonicalizerLoader(String version,String path) throws IllegalArgumentException, FileNotFoundException
    {
        if(Tools.getInstance().stringIsEmpty(version))
        {
            throw new IllegalArgumentException();
        }
        this.version = version;
        this.path = Paths.get(path,FILENAME+version+"-jar-with-dependencies.jar");
        
        if(!Files.exists(this.path))
        {
            throw new FileNotFoundException();
        }
    }
    
    
    public static MathCanonicalizerLoader newInstance(String version, String path) throws IllegalArgumentException, FileNotFoundException
    {
        return new MathCanonicalizerLoader(version, path);
    }
    
    
    
    public void execute()
    {
        if(path == null)
        {
            throw new RuntimeException();
        }
        
        URL jarFile = null;
        try
        {
            jarFile = path.toUri().toURL();
        }
        catch(MalformedURLException me)
        {
            me.printStackTrace();
        }
        
        URLClassLoader cl = URLClassLoader.newInstance(new URL[]{jarFile});
        
        Class MathMLCanonicalizerCommandLineTool = null;
        try
        {
            MathMLCanonicalizerCommandLineTool = cl.loadClass("cz.muni.fi.mir.mathmlcanonicalization."+MAIN_CLASS);
        }
        catch(ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        
        if(MathMLCanonicalizerCommandLineTool != null)
        {
            Method main = null;
            try
            {
                main = MathMLCanonicalizerCommandLineTool.getMethod("main",new Class[]{String[].class});
            } catch (    NoSuchMethodException | SecurityException ex)
            {
                Logger.getLogger(MathCanonicalizerLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(main != null)
            {
                String[] args = {"-c","C:\\Users\\Dominik\\Downloads\\MathMLCan-master\\MathMLCan-master\\target\\sample-config.xml","C:\\Users\\Dominik\\Downloads\\MathMLCan-master\\MathMLCan-master\\target\\sample-mathml.xml"};
                try
                {
                    main.invoke(MathMLCanonicalizerCommandLineTool,new Object[]{args});
                } catch (IllegalAccessException ex)
                {
                    Logger.getLogger(MathCanonicalizerLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex)
                {
                    Logger.getLogger(MathCanonicalizerLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex)
                {
                    Logger.getLogger(MathCanonicalizerLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
}
