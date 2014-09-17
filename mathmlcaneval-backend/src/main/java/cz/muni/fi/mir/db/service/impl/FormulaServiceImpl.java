/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

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
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.tools.XMLUtils;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai
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
    private FileDirectoryService fileDirectoryService;
    @Autowired
    private ApplicationRunDAO applicationRunDAO;
    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;
    @Autowired
    private XMLUtils xmlUtils;
    @Autowired
    private AnnotationDAO annotationDAO;

    @Override
    @Transactional(readOnly = false)
    public void createFormula(Formula formula) throws IllegalArgumentException
    {
        checkNull(formula);

        formulaDAO.createFormula(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateFormula(Formula formula) throws IllegalArgumentException
    {
        checkNull(formula);

        if (formula.getId() == null || formula.getId().compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given formula does not have valid id [" + formula.getId() + "].");
        }

        formulaDAO.updateFormula(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFormula(Formula formula) throws IllegalArgumentException
    {
        checkNull(formula);

        if (formula.getId() == null || formula.getId().compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given formula does not have valid ID [" + formula.getId() + "].");
        }
        for(CanonicOutput co : formula.getOutputs())
        {
            co.setApplicationRun(null);
        }

        formulaDAO.deleteFormula(formula);
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByID(Long id) throws IllegalArgumentException
    {
        if (id == null || id.compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given ID is not valid [" + id + "].");
        }
        return formulaDAO.getFormulaByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocumen)
    {
        return formulaDAO.getFormulasBySourceDocument(sourceDocumen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasByProgram(Program program)
    {
        return formulaDAO.getFormulasByProgram(program);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasByUser(User user)
    {
        return formulaDAO.getFormulasByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getAllFormulas()
    {
        return formulaDAO.getAllFormulas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getAllFormulas(int skip, int number)
    {
        return formulaDAO.getAllFormulas(skip, number);
    }

    @Override
    @Transactional(readOnly = false)
    @Async
    public void massFormulaImport(String path, String filter, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument, User user)
    {
        if(user == null)
        {
            throw new IllegalArgumentException("User is null");
        }
        ApplicationRun applicationRun = EntityFactory.createApplicationRun();
        applicationRun.setUser(user);
        logger.info(applicationRun.getUser());
        applicationRun.setRevision(revision);
        applicationRun.setConfiguration(configuration);

        List<Formula> toImport = Collections.emptyList();
        try
        {
            toImport = fileDirectoryService.exploreDirectory(path, filter);
        }
        catch (FileNotFoundException ex)
        {
            logger.error(ex);
        }
        if (!toImport.isEmpty())
        {
            logger.fatal("Attempt to create Application Run with flush mode to ensure its persisted.");
            applicationRunDAO.createApplicationRunWithFlush(applicationRun);
            logger.fatal("Operation withFlush called.");
            
            List<Formula> filtered = new ArrayList<>();
            for (Formula f : toImport)
            {
                String hash = Tools.getInstance().SHA1(f.getXml());
                Long id = formulaDAO.exists(hash);
                if (id == null)
                {
                    f.setHashValue(hash);
                    f.setProgram(program);
                    f.setUser(user);
                    f.setSourceDocument(sourceDocument);

                    extractElements(f);

                    attachElements(f);
                    formulaDAO.createFormula(f);

                    filtered.add(f);
                }
                else
                {
                    logger.info("Formula already exists with ID [" + id + "] - skipping.");
                }
            }

            if(filtered.isEmpty())
            {
                logger.warn("No formulas are going to be imported because they are already presented.");
            }
            
            mathCanonicalizerLoader.execute(filtered, applicationRun);

        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfRecords()
    {
        return formulaDAO.getNumberOfRecords();
    }

    @Override
    @Transactional(readOnly = false)
    public void recalculateHashes(boolean force)
    {
        List<Formula> formulas = null;

        if (force)
        {
            formulas = formulaDAO.getAllFormulas();
        }
        else
        {
            formulas = formulaDAO.getAllForHashing();
        }

        for (Formula f : formulas)
        {
            f.setHashValue(Tools.getInstance().SHA1(f.getXml()));
            formulaDAO.updateFormula(f);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void recalculateHash(Formula formula)
    {
        Formula f = formulaDAO.getFormulaByID(formula.getId());
        if (f != null)
        {
            f.setHashValue(Tools.getInstance().SHA1(f.getXml()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByHash(String hash) throws IllegalArgumentException
    {
        if (hash == null || hash.length() < 40)
        {
            throw new IllegalArgumentException("Invalid hash value [" + hash + "].");
        }

        return formulaDAO.getFormulaByHash(hash);
    }

    @Override
    @Transactional(readOnly = false)
    public void simpleFormulaImport(String formulaXmlContent, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument,User user)
    {
        if(user == null)
        {
            throw new IllegalArgumentException("User is null");
        }
        ApplicationRun appRun = EntityFactory.createApplicationRun();
        appRun.setUser(user);
        appRun.setRevision(revision);
        appRun.setConfiguration(configuration);

        applicationRunDAO.createApplicationRun(appRun);

        Formula f = EntityFactory.createFormula();
        f.setOutputs(new ArrayList<CanonicOutput>());
        f.setXml(formulaXmlContent);
        f.setInsertTime(DateTime.now());
        f.setUser(user);
        f.setHashValue(Tools.getInstance().SHA1(f.getXml()));

        extractElements(f);

        if (null == formulaDAO.getFormulaByHash(f.getHashValue()))
        {
            attachElements(f);
            formulaDAO.createFormula(f);

            mathCanonicalizerLoader.execute(Arrays.asList(f), appRun);
        }
        else
        {
            logger.info("Formula with hash [" + f.getHashValue() + "] is already in database.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Formula> getFormulasByElements(Collection<Element> collection, int start, int end)
    {
        return formulaDAO.getFormulasByElements(collection, start, end);
    }

    @Override
    @Transactional(readOnly = false)
    public void recalculateElements(boolean force)
    {
        List<Formula> formulas = formulaDAO.getAllFormulas(force);
        for (Formula f : formulas)
        {
            extractElements(f);
            formulaDAO.updateFormula(f);
        }
    }

    private void extractElements(Formula f)
    {
        Set<Element> temp = new HashSet<>();

        org.w3c.dom.Document doc = xmlUtils.parse(f.getXml());

        if (doc != null)
        {
            org.w3c.dom.NodeList nodeList = doc.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                temp.add(EntityFactory.createElement(nodeList.item(i).getNodeName()));
            }
        }
        List<Element> result = null;

        if (f.getElements() == null || f.getElements().isEmpty())
        {
            result = new ArrayList<>(temp.size());
        }
        else
        {
            result = new ArrayList<>(f.getElements());
        }

        result.addAll(temp);

        f.setElements(result);
    }

    private void checkNull(Formula f) throws IllegalArgumentException
    {
        if (f == null)
        {
            throw new IllegalArgumentException("Given formula is null.");
        }
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
                        elementDAO.createElement(e);
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
    @Transactional
    public void reindex()
    {
        formulaDAO.reindex();
    }

    @Override
    @Transactional(readOnly = false)
    public List<Formula> findSimilar(Formula formula,Map<String,String> properties,boolean override,boolean directWrite)
    {
        return formulaDAO.findSimilar(formula,properties,override,directWrite);
    }

    @Override
    @Transactional(readOnly = false)
    public void findSimilarMass(Map<String,String> properties)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false)
    public void attachSimilarFormulas(Formula formula, Long[] similarIDs, boolean override) throws IllegalArgumentException
    {
        if(formula == null)
        {
            throw new IllegalArgumentException("Input formula is null");
        }
        if(formula.getId() == null)
        {
            throw new IllegalArgumentException("Input formula does not have set its ID");
        }
        if(similarIDs == null)
        {
            throw new IllegalArgumentException("Input similarIDs is null");
        }
        
        if(similarIDs.length > 0)
        {
            d("Size is > 0. Starting attaching.");
            List<Formula> similarsToAdd = new ArrayList<>();
            if(!override && formula.getSimilarFormulas() != null)
            {
                d("Override disabled, adding previous similar forms.");
                similarsToAdd.addAll(formula.getSimilarFormulas());
            } 
            
            for(Long id : similarIDs)
            {   // because similar formulas are set
                // as cascade refresh hibernate needs only IDs
                Formula f  = EntityFactory.createFormula(id);
                d("Adding following formula: "+f);
                similarsToAdd.add(f);
            }
            
            formula.setSimilarFormulas(similarsToAdd);
            
            d("Task done with following output to be set:" 
                    + formula.getSimilarFormulas());
            //todo x sublist addition
            //so if A{w,x,y} where w,x,y are similar then set
            // x{w,y,A} w{A,x,y} and y{w,x,A} as similar
            formulaDAO.updateFormula(formula);
        }
    }
    
    
    /**
     * Method logs input with debug level into logger
     * @param s to be logged
     */
    private void d(String s)
    {
        logger.debug(s);
    }

    @Override
    @Transactional(readOnly = false)
    public void massRemove(List<Formula> toBeRemoved)
    {
        for(Formula f : toBeRemoved)
        {            
            formulaDAO.deleteFormula(f);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void annotateFormula(Formula formula, Annotation annotation)
    {
        annotationDAO.createAnnotation(annotation);
        
        List<Annotation> current = new ArrayList<>();
        
        if(formula.getAnnotations() != null && !formula.getAnnotations().isEmpty())
        {
            current.addAll(formula.getAnnotations());
        }
        
        current.add(annotation);
        
        formula.setAnnotations(current);
        
        formulaDAO.updateFormula(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAnnotationFromFormula(Formula formula, Annotation annotation)
    {
        List<Annotation> temp = new ArrayList<>(formula.getAnnotations());
        
        temp.remove(annotation);
        
        formula.setAnnotations(temp);
        
        formulaDAO.updateFormula(formula);
        
        annotationDAO.deleteAnnotation(annotation);
    }

    @Override
	@Async
    public void massCanonicalize(List<Long> listOfIds, Revision revision, Configuration configuration, User user)
    {
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
            formula.getOutputs().size();
            toCanonicalize.add(formula);
        }
        if (!toCanonicalize.isEmpty())
        {
            logger.fatal("Attempt to create Application Run with flush mode to ensure its persisted.");
            applicationRunDAO.createApplicationRunWithFlush(applicationRun);
            logger.fatal("Operation withFlush called.");

            mathCanonicalizerLoader.execute(toCanonicalize, applicationRun);
        }
    }
}
