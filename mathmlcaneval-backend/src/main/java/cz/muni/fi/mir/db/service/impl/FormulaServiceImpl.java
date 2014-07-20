/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
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
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FileDirectoryService fileDirectoryService;
    @Autowired
    private SecurityContextFacade securityContext;
    @Autowired
    private ApplicationRunDAO applicationRunDAO;
    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;
    @Autowired
    private XMLUtils xmlUtils;

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
        
        if(formula.getId() == null || formula.getId().compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given formula does not have valid id ["+formula.getId()+"].");
        }
        
        formulaDAO.updateFormula(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFormula(Formula formula) throws IllegalArgumentException
    {
        checkNull(formula);
        
        if(formula.getId() == null || formula.getId().compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given formula does not have valid ID ["+formula.getId()+"].");
        }
        
        formulaDAO.deleteFormula(formula);
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByID(Long id) throws IllegalArgumentException
    {
        if(id == null || id.compareTo(Long.valueOf("1")) < 0)
        {
            throw new IllegalArgumentException("Given ID is not valid ["+id+"].");
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
    public void massFormulaImport(String path, String filter, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument)
    {
        ApplicationRun applicationRun = EntityFactory.createApplicationRun();
        applicationRun.setUser(securityContext.getLoggedEntityUser());
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
            applicationRunDAO.createApplicationRun(applicationRun);
            List<Formula> filtered = new ArrayList<>();
            for (Formula f : toImport)
            {
                String hash = Tools.getInstance().SHA1(f.getXml());
                Long id = formulaDAO.exists(hash);
                if (id == null)
                {
                    f.setHash(hash);
                    f.setProgram(program);
                    f.setSourceDocument(sourceDocument);

                    extractElements(f);

                    formulaDAO.createFormula(f);

                    filtered.add(f);
                }
                else
                {
                    logger.info("Formula already exists with ID [" + id + "] - skipping.");
                }
            }

            if (filtered.isEmpty())
            {
                logger.info("No formulas are going to be imported because they are already presented.");
            }
            else
            {
                mathCanonicalizerLoader.execute(filtered, applicationRun);
            }
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
            f.setHash(Tools.getInstance().SHA1(f.getXml()));
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
            f.setHash(Tools.getInstance().SHA1(f.getXml()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByHash(String hash) throws IllegalArgumentException
    {
        if(hash == null || hash.length() < 40)
        {
            throw new IllegalArgumentException("Invalid hash value ["+hash+"].");
        }
        
        return formulaDAO.getFormulaByHash(hash);
    }

    @Override
    @Transactional(readOnly = false)
    public void simpleFormulaImport(String formulaXmlContent, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument)
    {
        ApplicationRun appRun = EntityFactory.createApplicationRun();
        appRun.setUser(securityContext.getLoggedEntityUser());
        appRun.setRevision(revision);
        appRun.setConfiguration(configuration);

        applicationRunDAO.createApplicationRun(appRun);

        Formula f = EntityFactory.createFormula();
        f.setOutputs(new ArrayList<CanonicOutput>());
        f.setXml(formulaXmlContent);
        f.setInsertTime(DateTime.now());
        f.setUser(securityContext.getLoggedEntityUser());
        f.setHash(Tools.getInstance().SHA1(f.getXml()));

        extractElements(f);

        if (null == formulaDAO.getFormulaByHash(f.getHashValue()))
        {
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
    public List<Formula> getFormulasByElements(Collection<Element> collection,int start, int end)
    {
        return formulaDAO.getFormulasByElements(collection,start,end);
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
            travelDocument(doc.getDocumentElement(), temp);
        }

        List<Element> result = new ArrayList<>(f.getElements());
        result.addAll(temp);

        f.setElements(result);
    }

    private void travelDocument(org.w3c.dom.Node node, Set<Element> temp)
    {
        if (node == null)
        {
            return;
        }
        else
        {
            org.w3c.dom.NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                org.w3c.dom.Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
                {
                    temp.add(EntityFactory.createElement(currentNode.getNodeName()));

                    travelDocument(node, temp);
                }
            }
        }
    }

    private void checkNull(Formula f) throws IllegalArgumentException
    {
        if (f == null)
        {
            throw new IllegalArgumentException("Given formula is null.");
        }
    }
}
