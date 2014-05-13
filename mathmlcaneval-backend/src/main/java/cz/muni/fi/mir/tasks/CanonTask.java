/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.tasks;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.tools.SimilarityFormConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;

/**
 *
 * @author emptak
 */
public class CanonTask implements Runnable
{
    private List<Formula> formulas;
    private ApplicationRun applicationRun;
    private Class mainClass;
    
    private CanonicOutputService canonicOutputService;
    private FormulaService formulaService;
    private ApplicationRunService applicationRunService;
    private SimilarityFormConverter similarityFormConverter;

    private static final Logger logger = Logger.getLogger(CanonicalizationTask.class);

    private CanonTask(CanonicOutputService canonicOutputService, FormulaService formulaService, ApplicationRunService applicationRunService)
    {
        this.canonicOutputService = canonicOutputService;
        this.formulaService = formulaService;
        this.applicationRunService = applicationRunService;
    }    
    
    public static CanonTask newInstance(CanonicOutputService canonicOutputService, FormulaService formulaService, ApplicationRunService applicationRunService)
    {
        CanonTask ct = new CanonTask(canonicOutputService, formulaService, applicationRunService);
        
        return ct;
    }
    
    
    public void setDependencies(List<Formula> formulas, ApplicationRun applicationRun, Class mainClass)
    {
        this.formulas = formulas;
        this.applicationRun = applicationRun;
        this.mainClass = mainClass;
    }
    
    @Override
    public void run()
    {
        Constructor constructor = null;
        Method canonicalize = null;
        Object canonicalizer = null;
        try
        {
            constructor = this.mainClass.getConstructor(InputStream.class);
            canonicalize = this.mainClass.getMethod("canonicalize", InputStream.class, OutputStream.class);
        }
        catch (NoSuchMethodException | SecurityException ex)
        {
            logger.fatal(ex);
        }
        
        InputStream config = new ByteArrayInputStream(applicationRun.getConfiguration().getConfig().getBytes());
        
        try
        {
            canonicalizer = constructor.newInstance(config);            
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex)
        {
            logger.fatal(ex);
        }
        
        DateTime startTime = DateTime.now();
        for(Formula f : formulas)
        {
            CanonicOutput co = canonicalize(f, canonicalizer, canonicalize,applicationRun);
            
            canonicOutputService.createCanonicOutput(co);
            // we need to force-fetch lazy collections. ugly..
            Hibernate.initialize(f.getOutputs()); 
            
            f.getOutputs().add(co);
            
            formulaService.updateFormula(f);
            
            logger.info(String.format("Formula %d canonicalized.", f.getId()));
        }
        
        
        DateTime stopTime = DateTime.now();
        applicationRun.setStartTime(startTime);
        applicationRun.setStopTime(stopTime);
        applicationRunService.updateApplicationRun(applicationRun);
    }
    
    
    private CanonicOutput canonicalize(Formula f, Object canonicalizer, Method canonicalize, ApplicationRun applicationRun)
    {
        InputStream input = new ByteArrayInputStream(f.getXml().getBytes());
        OutputStream output = new ByteArrayOutputStream();
        CanonicOutput co = new CanonicOutput();
        long start = System.currentTimeMillis();
        try
        {
            canonicalize.invoke(canonicalizer, input, output);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException  ex)
        {
            logger.fatal(ex);
        }
        
        co.setApplicationRun(applicationRun);
        co.setOutputForm(output.toString());
        //canonicOutput.setSimilarForm(similarityFormConverter.convert(canonicOutput.getOutputForm()));
        co.setRunningTime(System.currentTimeMillis()-start);
        co.setParents(Arrays.asList(f));
        
        return co;
    }
    
}
