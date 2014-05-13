/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
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
    
    
    @Override
    @Transactional(readOnly = false)
    public void createFormula(Formula formula)
    {
        formulaDAO.createFormula(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateFormula(Formula formula)
    {
        formulaDAO.updateFormula(formula);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFormula(Formula formula)
    {
        formulaDAO.deleteFormula(formula);
    }

    @Override
    @Transactional(readOnly = true)
    public Formula getFormulaByID(Long id)
    {
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
    public void massFormulaImport(String path, String filter, User user, Revision revision, Configuration configuration, Program program, SourceDocument sourceDocument)
    {
        ApplicationRun appRun = EntityFactory.createApplicationRun();
        appRun.setUser(securityContext.getLoggedEntityUser());
        appRun.setRevision(revision);
        appRun.setConfiguration(configuration);  
        
        massFormulaImport(path, filter, appRun, program, sourceDocument);
    }

    @Override
    @Transactional(readOnly = false)
    public void massFormulaImport(String path, String filter, ApplicationRun applicationRun, Program program, SourceDocument sourceDocument)
    {
        // move to loader ? or different thread
        List<Formula> toImport = Collections.emptyList();
        try
        {
            toImport = fileDirectoryService.exploreDirectory(path, filter);
        }
        catch(FileNotFoundException ex)
        {
            logger.error(ex);
        }
        if(!toImport.isEmpty())
        {
            applicationRunDAO.createApplicationRun(applicationRun);
            
            for(Formula f : toImport)
            {
                f.setProgram(program);
                f.setSourceDocument(sourceDocument);
                formulaDAO.createFormula(f);
            } 
            
            mathCanonicalizerLoader.execute(toImport, applicationRun);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfRecords()
    {
        return formulaDAO.getNumberOfRecords();
    }
}
