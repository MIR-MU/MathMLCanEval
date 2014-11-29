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
package cz.muni.fi.mir.db.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.dao.ElementDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SearchResponse;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ElementService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.scheduling.FormulaImportTask;
import cz.muni.fi.mir.scheduling.LongRunningTaskFactory;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.services.TaskService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.index.IndexTools;
import cz.muni.fi.mir.tools.Tools;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Service(value = "formulaService")
public class FormulaServiceImpl implements FormulaService
{
    private static final Logger logger = Logger.getLogger(FormulaServiceImpl.class);

    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private ElementDAO elementDAO;
    @Autowired
    private ApplicationRunDAO applicationRunDAO;
    @Autowired
    private ApplicationRunService applicationRunService;
    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;
    @Autowired
    private AnnotationDAO annotationDAO;
    @Autowired
    private LongRunningTaskFactory taskFactory;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ElementService elementService;
    @Autowired
    private IndexTools indexTools;

    @Override
    @Transactional(readOnly = false)
    public void createFormula(Formula formula) throws IllegalArgumentException
    {
        if(formula == null)
        {
            throw new IllegalArgumentException("Given input formula is null.");
        }

        formulaDAO.create(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateFormula(Formula formula) throws IllegalArgumentException
    {
        InputChecker.checkInput(formula);

        formulaDAO.update(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFormula(Formula formula) throws IllegalArgumentException
    {
        InputChecker.checkInput(formula);

        if (formula.getId() == null || formula.getId().compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given formula does not have valid ID [" + formula.getId() + "].");
        }

        formulaDAO.deleteFormula(formula);
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByID(Long id) throws IllegalArgumentException
    {
        if (id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given ID is not valid [" + id + "].");
        }
        return formulaDAO.getFormulaByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByAnnotation(Annotation annotation) throws IllegalArgumentException
    {
        InputChecker.checkInput(annotation);
        
        return formulaDAO.getFormulaByAnnotation(annotation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getAllFormulas(Pagination pagination) throws IllegalArgumentException
    {
        InputChecker.checkInput(pagination);
        
        return formulaDAO.getAllFormulas(pagination);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getAllFormulas() throws IllegalArgumentException
    {
        return formulaDAO.getAllFormulas();
    }

    @Override
    @Transactional(readOnly = false)
    public void massFormulaImport(String path, String filter, Revision revision, 
            Configuration configuration, Program program, 
            SourceDocument sourceDocument, User user) throws IllegalArgumentException
    {
        InputChecker.checkInput(configuration);
        InputChecker.checkInput(revision);
        InputChecker.checkInput(program);
        InputChecker.checkInput(sourceDocument);
        InputChecker.checkInput(user);
        
        if(path == null || path.length() < 1)
        {
            throw new IllegalArgumentException("Empty path was passed for import.");
        }
        
        FormulaImportTask task = taskFactory.createImportTask();
        task.setDependencies(path, filter, revision, configuration, program, sourceDocument, user);
        taskService.submitTask(task);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfRecords()
    {
        return formulaDAO.getNumberOfRecords();
    }

    @Override
    @Transactional(readOnly = false)
    public void simpleFormulaImport(String formulaXmlContent, Revision revision, 
            Configuration configuration, Program program, 
            SourceDocument sourceDocument, User user) throws IllegalArgumentException
    {
        InputChecker.checkInput(configuration);
        InputChecker.checkInput(revision);
        InputChecker.checkInput(program);
        InputChecker.checkInput(sourceDocument);
        InputChecker.checkInput(user);
        
        ApplicationRun appRun = EntityFactory.createApplicationRun();
        appRun.setUser(user);
        appRun.setRevision(revision);
        appRun.setConfiguration(configuration);

        applicationRunDAO.create(appRun);

        Formula f = EntityFactory.createFormula();
        f.setOutputs(new ArrayList<CanonicOutput>());
        f.setXml(formulaXmlContent);
        f.setInsertTime(DateTime.now());
        f.setUser(user);
        f.setHashValue(Tools.getInstance().SHA1(f.getXml()));

        f.setElements(elementService.extractElements(f));

        if (null == formulaDAO.getFormulaByHash(f.getHashValue()))
        {
            attachElements(f);
            formulaDAO.create(f);

            mathCanonicalizerLoader.execute(Arrays.asList(f), appRun);
        }
        else
        {
            logger.info("Formula with hash [" + f.getHashValue() + "] is already in database.");
        }
    }

    @Override
    @Transactional
    public void reindexAndOptimize()
    {
        indexTools.reIndexClass(Formula.class);
        indexTools.optimize(Formula.class);
    }

    @Override
    @Transactional(readOnly = false)
    public SearchResponse<Formula> findSimilar(Formula formula,
            Map<String,String> properties, boolean override, boolean crosslink,
            boolean directWrite, Pagination pagination) throws IllegalArgumentException
    {
        InputChecker.checkInput(pagination);
        InputChecker.checkInput(formula);
        
        if(properties == null || properties.isEmpty())
        {
            throw new IllegalArgumentException("Given input map of properties is empty.");
        }
        
        return formulaDAO.findSimilar(formula,properties,override,crosslink,directWrite, pagination);
    }

    @Override
    @Transactional(readOnly = false)
    public void attachSimilarFormulas(Formula formula, Long[] similarIDs, boolean override) throws IllegalArgumentException
    {
        InputChecker.checkInput(formula);
        
        if(similarIDs == null || similarIDs.length == 0)
        {
            throw new IllegalArgumentException("Input similarIDs is empty.");
        }
        
        if(similarIDs.length > 0)
        {
            formulaDAO.attachSimilarFormulas(formula, similarIDs, override);
        }
    }
    
    @Override
    @Transactional(readOnly = false)
    public void massRemove(List<Formula> toBeRemoved) throws IllegalArgumentException
    {
        if(toBeRemoved == null || toBeRemoved.isEmpty())
        {
            throw new IllegalArgumentException("Formulas marked for input are empty.");
        }
        
        for(Formula f : toBeRemoved)
        {  
            InputChecker.checkInput(f);
            
            formulaDAO.deleteFormula(f);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void annotateFormula(Formula formula, Annotation annotation) throws IllegalArgumentException
    {
        InputChecker.checkInput(formula);
        if(annotation == null)
        {
            throw new IllegalArgumentException("Given annotation is null");
        }
        annotationDAO.create(annotation);
        
        List<Annotation> current = new ArrayList<>();
        
        if(formula.getAnnotations() != null && !formula.getAnnotations().isEmpty())
        {
            current.addAll(formula.getAnnotations());
        }
        
        current.add(annotation);
        
        formula.setAnnotations(current);
        
        formulaDAO.update(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAnnotationFromFormula(Formula formula, Annotation annotation) throws IllegalArgumentException
    {
        InputChecker.checkInput(formula);
        InputChecker.checkInput(annotation);
        
        List<Annotation> temp = new ArrayList<>(formula.getAnnotations());
        
        temp.remove(annotation);
        
        formula.setAnnotations(temp);
        
        formulaDAO.update(formula);
        
        annotationDAO.delete(annotation.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResponse<Formula> findFormulas(FormulaSearchRequest formulaSearchRequest, Pagination pagination) throws IllegalArgumentException
    {
        InputChecker.checkInput(pagination);
        if(formulaSearchRequest == null)
        {
            throw new IllegalArgumentException("Given request is empty.");
        }
        
        return formulaDAO.findFormulas(formulaSearchRequest, pagination);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResponse<Formula> findFormulas(FormulaSearchRequest formulaSearchRequest) throws IllegalArgumentException
    {
        if(formulaSearchRequest == null)
        {
            throw new IllegalArgumentException("Given request is empty.");
        }

        return formulaDAO.findFormulas(formulaSearchRequest);
    }

    @Override
    public void massCanonicalize(List<Long> listOfIds, Revision revision, Configuration configuration, User user) throws IllegalArgumentException
    {
        InputChecker.checkInput(configuration);
        InputChecker.checkInput(revision);
        InputChecker.checkInput(user);
        
        if(listOfIds == null || listOfIds.isEmpty())
        {
            throw new IllegalArgumentException("GIven input list marked for deletion is empty, or null.");
        }
        
        ApplicationRun applicationRun = EntityFactory.createApplicationRun();
        applicationRun.setUser(user);
        applicationRun.setRevision(revision);
        applicationRun.setConfiguration(configuration);

        List<Formula> toCanonicalize = new ArrayList<>();
        for (Long formulaID : listOfIds)
        {
            Formula formula = formulaDAO.getFormulaByID(formulaID);
            // for some reason, the session is already closed in the task,
            // so we need to fetch to lazy collection while we have it...
            Hibernate.initialize(formula.getOutputs());
            for (CanonicOutput co : formula.getOutputs())
            {
                Hibernate.initialize(co.getAnnotations());
            }
            toCanonicalize.add(formula);
        }
        if (!toCanonicalize.isEmpty())
        {
            logger.fatal("Attempt to create Application Run with flush mode to ensure its persisted.");
            applicationRunService.createApplicationRun(applicationRun,true);
            logger.fatal("Operation withFlush called.");

            mathCanonicalizerLoader.execute(toCanonicalize, applicationRun);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocumen) throws IllegalArgumentException
    {
        InputChecker.checkInput(sourceDocumen);
        
        return formulaDAO.getFormulasBySourceDocument(sourceDocumen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasByProgram(Program program) throws IllegalArgumentException
    {
        InputChecker.checkInput(program);
        
        return formulaDAO.getFormulasByProgram(program);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasByUser(User user) throws IllegalArgumentException
    {
        InputChecker.checkInput(user);
        
        return formulaDAO.getFormulasByUser(user);
    }
    
     /**
     * Method logs input with debug level into logger
     * @param s to be logged
     */
    private void d(String s)
    {
        logger.debug(s);
    }

    /**
     * Method takes elements from formula and matches them against already
     * persisted list of elements. If element already exist then it has id in
     * obtained list (from database) and id for element in formula is set.
     * Otherwise we check temp list which contains newly created elements. If
     * there is no match then new element is created and stored in temp list.
     * Equals method somehow fails on CascadeType.ALL, so this is reason why we
     * have to do manually. TODO redo in future. Possible solution would be to
     * have all possible elements already stored inside database.
     *
     * @param f formula of which we attach elements.
     */
    private void attachElements(Formula f)
    {
        if (f.getElements() != null && !f.getElements().isEmpty())
        {
            List<Element> list = elementDAO.getAllElements();
            List<Element> newList = new ArrayList<>();
            for (Element e : f.getElements())
            {
                int index = list.indexOf(e);
                if (index == -1)
                {
                    int index2 = newList.indexOf(e);
                    if (index2 == -1)
                    {
                        elementDAO.create(e);
                        newList.add(e);
                    }
                    else
                    {
                        e.setId(newList.get(index2).getId());
                    }
                }
                else
                {
                    e.setId(list.get(index).getId());
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasByCanonicOutputHash(String hash) throws IllegalArgumentException
    {
        if(hash == null || hash.length() != 40)
        {
            throw new IllegalArgumentException("Wrong sha1 fingerprint. Length should be 40 but was ["+hash+"]");
        }
        
        return formulaDAO.getFormulasByCanonicOutputHash(hash);
    }
}
