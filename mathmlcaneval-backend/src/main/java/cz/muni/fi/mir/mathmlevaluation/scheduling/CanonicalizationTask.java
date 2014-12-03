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
package cz.muni.fi.mir.mathmlevaluation.scheduling;

import cz.muni.fi.mir.mathmlevaluation.db.domain.Annotation;

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
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Formula;
import cz.muni.fi.mir.mathmlevaluation.db.service.AnnotationService;
import cz.muni.fi.mir.mathmlevaluation.db.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlevaluation.db.service.CanonicOutputService;
import cz.muni.fi.mir.mathmlevaluation.db.service.FormulaService;
import cz.muni.fi.mir.mathmlevaluation.db.service.UserService;
import cz.muni.fi.mir.mathmlevaluation.exceptions.CanonicalizerException;
import cz.muni.fi.mir.mathmlevaluation.scheduling.TaskStatus.TaskType;
import cz.muni.fi.mir.mathmlevaluation.services.MailService;
import cz.muni.fi.mir.mathmlevaluation.tools.Tools;
import cz.muni.fi.mir.mathmlevaluation.tools.xml.XMLUtils;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("${annotation.sameoutput}")
    private String sameOutputAnnotation;

    @Value("${annotation.copyprefix}")
    private String copyPrefixAnnotation;

    @Value("${annotation.changedetected}")
    private String changeDetectedAnnotation;
    
    @Autowired
    private CanonicOutputService canonicOutputService;
    @Autowired
    private FormulaService formulaService;
    @Autowired
    private ApplicationRunService applicationRunService;
    @Autowired
    private AnnotationService annotationService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private XMLUtils xmlUtils;

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
        getStatus().setTaskType(TaskType.CANONICALIZATION);
    }

    @Override
    public TaskStatus call() throws IllegalStateException, CanonicalizerException
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
                throw new CanonicalizerException(ex.getMessage(),ex.getCause());
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
                    logger.info(ex.getMessage(), ex.getCause());
                    throw new CanonicalizerException(ex.getMessage(),ex.getCause());
                }

                DateTime startTime = DateTime.now();
                getStatus().setStartTime(startTime);
                applicationRun.setStartTime(startTime);
                for (Formula f : formulas)
                {
                    CanonicOutput co = canonicalize(f, canonicalizer, canonicalize, applicationRun);
                    String hashValue = Tools.getInstance().SHA1(co.getOutputForm());
                    co.setHashValue(hashValue);
                    
                    validateAgainstDTD(co);
                    
                    List<Annotation> autoAnnotations = new ArrayList<>();
                    
                    for (CanonicOutput prevco : f.getOutputs())
                    {
                        if (hashValue.equals(prevco.getHashValue()))
                        {
                            for (Annotation a : prevco.getAnnotations())
                            {
                                if (!a.getAnnotationContent().startsWith(copyPrefixAnnotation) && !autoAnnotations.contains(a))
                                {
                                    Annotation copy = new Annotation();
                                    copy.setUser(a.getUser());
                                    copy.setAnnotationContent(copyPrefixAnnotation + a.getAnnotationContent().substring(1));
                                    autoAnnotations.add(copy);

                                    annotationService.createAnnotation(copy);
                                }
                            }
                        }
                    }

                    CanonicOutput previousCo = canonicOutputService.lastOfFormula(f);
                    if (previousCo != null)
                    {
                        if (!hashValue.equals(previousCo.getHashValue()))
                        {
                            Annotation a = new Annotation();
                            a.setUser(userService.getSystemUser());
                            a.setAnnotationContent(changeDetectedAnnotation);
                            autoAnnotations.add(a);

                            annotationService.createAnnotation(a);
                        }
                    }

                    List<Formula> sameHashFormulas = formulaService.getFormulasByCanonicOutputHash(co.getHashValue());
                    logger.info(sameHashFormulas);
                    
                    if(!sameHashFormulas.isEmpty())
                    {
                        for(Formula shf : sameHashFormulas)
                        {
                            if(shf.equals(f))
                            {
                                sameHashFormulas.remove(f);
                                break;
                            }
                        }
                        if(!sameHashFormulas.isEmpty())
                        {
                            Annotation a = new Annotation();
                            a.setUser(userService.getSystemUser());
                            StringBuilder sb = new StringBuilder();
                            sb.append(sameOutputAnnotation)
                                    .append(" [");
                            for(Formula shf : sameHashFormulas)
                            {
                                sb.append(shf.getId()).append(", ");
                            }
                            sb.append("]");
                            a.setAnnotationContent(sb.toString());

                            annotationService.createAnnotation(a);

                            autoAnnotations.add(a);

                            List<Formula> parents = new ArrayList<>(co.getParents());
                            parents.addAll(sameHashFormulas);
                            co.setParents(parents);
                        }
                        else
                        {
                            logger.info("No same matches found for canonic output ["+co.getHashValue()+"]");
                        }
                    }
                    
                    f.getOutputs().add(co);
                    
                    canonicOutputService.createCanonicOutput(co);

                    if (autoAnnotations.size() > 0)
                    {
                        co.setAnnotations(autoAnnotations);
                        canonicOutputService.updateCanonicOutput(co);
                        logger.info(autoAnnotations.size() + " automatic annotations added to canonicoutput " + co.getId());
                    }
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
    
    private void validateAgainstDTD(CanonicOutput co)
    {
        if(co.getOutputForm() != null && co.getOutputForm().length() > 0)
        {
            String message = xmlUtils.isValid(co.getOutputForm());
            
            if(message != null && !message.equals(StringUtils.EMPTY))
            {
                Annotation a = new Annotation();
                a.setAnnotationContent("#isInvalid "+message);
                a.setUser(userService.getSystemUser());
                
                annotationService.createAnnotation(a);
                List<Annotation> annotations = new ArrayList<>();
                if(co.getAnnotations() != null)
                {
                    annotations.addAll(co.getAnnotations());
                }
                
                annotations.add(a);
                
                co.setAnnotations(annotations);
            }
        }
    }
}
