/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cz.muni.fi.mir.scheduling;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.scheduling.TaskStatus.TaskType;
import cz.muni.fi.mir.services.MailService;
import cz.muni.fi.mir.tools.Tools;

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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO SIMALIRITY FORM CONVERSION
 * TODO BETTER EXCEPTION AND ERROR STATE HANDLING.
 * 
 * @author Dominik Szalai
 * @since 1.0
 * @version 1.0
 */
public class CanonicalizationTask extends ApplicationTask
{

    private List<Formula> formulas;
    private ApplicationRun applicationRun;
    private Class mainClass;

    @Autowired
    private CanonicOutputService canonicOutputService;
    @Autowired
    private FormulaService formulaService;
    @Autowired
    private ApplicationRunService applicationRunService;
    @Autowired
    private MailService mailService;

    private static final Logger logger = Logger.getLogger(CanonicalizationTask.class);


    /**
     * Method setups data dependencies used for CanonicalizationTask.
     *
     * @param formulas List of formulas to be Canonicalized
     * @param applicationRun ApplicationRun under which canonicalization runs
     * @param mainClass Target class of canonicalizer, either local or remote
     * from jar file.
     * @throws IllegalArgumentException if any of input parameter is null, has
     * null id, or List of formulas is empty.
     */
    public void setDependencies(List<Formula> formulas, ApplicationRun applicationRun, Class mainClass) throws IllegalArgumentException
    {
        if (Tools.getInstance().isEmpty(formulas))
        {
            throw new IllegalArgumentException("List of formulas is null or contains no formulas.");
        }
        if (applicationRun == null)
        {
            throw new IllegalArgumentException("ApplicationRun is null");
        }
        if (applicationRun.getId() == null)
        {
            throw new IllegalArgumentException("ApplicationRun does not have set its id.");
        }
        if (mainClass == null)
        {
            throw new IllegalArgumentException("Main class is not set. Current value is null.");
        }

        this.formulas = formulas;
        this.applicationRun = applicationRun;
        this.mainClass = mainClass;

        setStatus(new TaskStatus());
        getStatus().setTaskType(TaskType.canonicalization);
    }

    @Override
    public TaskStatus call() throws IllegalStateException
    {
        String message = "";
            message += "formulas isSet? [" + (formulas != null) + "], ";
            message += "applicationrun isSet? [" + (applicationRun != null) + "], ";
            message += "mainClass isSet? [" + (mainClass != null) + "].";
         
        logger.info(message);
        logger.info(applicationRun);
        if (formulas == null || formulas.isEmpty() || applicationRun == null || mainClass == null)
        {
            throw new IllegalStateException(message);
        }
        else
        {
            Constructor constructor = null;
            Method canonicalize = null;
            Object canonicalizer = null;
            getStatus().setTotal(formulas.size());
            getStatus().setCurrent(0);
            getStatus().setUser(applicationRun.getUser());
            getStatus().setNote(String.valueOf(applicationRun.getId()));
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
            if (constructor != null)
            {
                try
                {
                    canonicalizer = constructor.newInstance(config);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex)
                {
                    logger.fatal(ex);
                }

                DateTime startTime = DateTime.now();
                getStatus().setStartTime(startTime);
                applicationRun.setStartTime(startTime);
                for (Formula f : formulas)
                {
                    CanonicOutput co = canonicalize(f, canonicalizer, canonicalize, applicationRun);

                    canonicOutputService.createCanonicOutput(co);
                    Hibernate.initialize(f.getOutputs());

                    f.getOutputs().add(co);

                    formulaService.updateFormula(f);

                    logger.info(String.format("Formula %d canonicalized.", f.getId()));
                    getStatus().setCurrent(getStatus().getCurrent() + 1);
                }

                DateTime stopTime = DateTime.now();
                getStatus().setStopTime(stopTime);
                applicationRun.setStopTime(stopTime);
                applicationRunService.updateApplicationRun(applicationRun);
                
                String mailMessage = "Canonicalization started at %s has finished. The running time was "
                        +" %s ms and %d formulas were canonicalized. Task finished at %s";
                
                mailService.sendMail(null,
                        applicationRun.getUser().getEmail(),
                        "Canonicalization has finished.", 
                        String.format(mailMessage, 
                                applicationRun.getStartTime().toString(),
                                (applicationRun.getStopTime().getMillis()-applicationRun.getStartTime().getMillis()),
                                formulas.size(),
                                applicationRun.getStopTime().toString())
                        );
            }
            return getStatus();
        }
    }

    /**
     * Method creates CanonicOutput out of given formula. For proper
     * canonicalization we need input formula, already instantiated
     * Canonicalizer via reflection, main method obtained via reflection and
     * ApplicationRun under which canonicalization task runs.
     *
     * @param f formula to be canonicalized
     * @param canonicalizer instance of Canonicalizer
     * @param canonicalize main method
     * @param applicationRun under which task runs
     * @return Canonic Output based on given formula.
     */
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
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            logger.fatal(ex);
        }

        co.setApplicationRun(applicationRun);
        co.setOutputForm(output.toString());
        //canonicOutput.setSimilarForm(similarityFormConverter.convert(canonicOutput.getOutputForm()));
        co.setRunningTime(System.currentTimeMillis() - start);
        co.setParents(Arrays.asList(f));

        return co;
    }

}
