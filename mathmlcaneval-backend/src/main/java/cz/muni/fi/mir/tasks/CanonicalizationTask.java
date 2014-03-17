/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tasks;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.FormulaService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;


/**
 *
 * @author siska
 */
public class CanonicalizationTask implements Callable<TaskStatus>
{
    private Formula formula;
    private ApplicationRun applicationRun;
    private Class mainClass;

    private FormulaService formulaService;
    private ApplicationRunService applicationRunService;

    private static final Logger logger = Logger.getLogger(CanonicalizationTask.class);

    public CanonicalizationTask(FormulaService formulaService, Formula formula, Class mainClass, ApplicationRunService applicationRunService, ApplicationRun applicationRun)
    {
        this.formulaService = formulaService;
        this.applicationRunService = applicationRunService;
        this.formula = formula;
        this.applicationRun = applicationRun;
        this.mainClass = mainClass;
    }

    @Override
    public TaskStatus call() throws Exception
    {
        DateTime startTime = DateTime.now();

        if (this.mainClass == null)
        {
            logger.fatal("Main Class is null.");
            return new TaskStatus();
        }

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
        InputStream input = new ByteArrayInputStream(formula.getXml().getBytes());
        OutputStream output = new ByteArrayOutputStream();
        try
        {
            canonicalizer = constructor.newInstance(config);
            canonicalize.invoke(canonicalizer, input, output);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex)
        {
            logger.fatal(ex);
        }
        DateTime stopTime = DateTime.now();
        applicationRun.setStartTime(startTime);
        applicationRun.setStopTime(stopTime);
        applicationRunService.updateApplicationRun(applicationRun);

        CanonicOutput canonicOutput = new CanonicOutput();
        canonicOutput.setApplicationRun(applicationRun);
        canonicOutput.setOutputForm(output.toString());
        canonicOutput.setRunningTime(stopTime.getMillis() - startTime.getMillis());

        canonicOutput.setParents(Arrays.asList(formula));
        formula.getOutputs().add(canonicOutput);

        formulaService.updateFormula(formula);

        logger.info(String.format("Formula %d canonicalized.", formula.getId()));

        return new TaskStatus();
    }
}
