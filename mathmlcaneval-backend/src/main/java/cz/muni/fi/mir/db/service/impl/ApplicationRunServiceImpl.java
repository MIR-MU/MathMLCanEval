/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.scheduling.ApplicationRunRemovalTask;
import cz.muni.fi.mir.scheduling.LongRunningTaskFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "applicationRunService")
public class ApplicationRunServiceImpl implements ApplicationRunService
{

    private static final Logger logger = Logger.getLogger(ApplicationRunServiceImpl.class);

    @Autowired
    private ApplicationRunDAO applicationRunDAO;
    @Autowired
    private CanonicOutputDAO canonicOutputDAO;
    @Autowired
    private FormulaDAO formulaDAO;

    @Autowired
    private LongRunningTaskFactory longRunningTaskFactory;
    @Autowired
    @Qualifier(value = "taskExecutor")
    private AsyncTaskExecutor taskExecutor;

    @Override
    @Transactional(readOnly = false)
    public void createApplicationRun(ApplicationRun applicationRun)
    {
        applicationRunDAO.createApplicationRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateApplicationRun(ApplicationRun applicationRun)
    {
        applicationRunDAO.updateApplicationRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteApplicationRun(ApplicationRun applicationRun,
            boolean deleteFormulas,
            boolean deleteCanonicOutputs)
    {
        //phase one obtain all outputs that belongs to run
        List<CanonicOutput> canonicOutputs = canonicOutputDAO.getCanonicOutputByAppRun(applicationRun);

        // if we do not wish to delete formulas
        if (!deleteFormulas)
        {
            logger.debug("Not deleting any formulas.");
            // we iterate over each co that belongs to apprun
            for (CanonicOutput co : canonicOutputs)
            {
                // if we want to delete outputs
                if (deleteCanonicOutputs)
                {   // we simply delete them
                    canonicOutputDAO.deleteCanonicOutput(co);
                }
                else
                {   //otherwise we remove reference to apprun and update it
                    co.setApplicationRun(null);
                    canonicOutputDAO.updateCanonicOutput(co);
                }
            }
        }
        else
        {   //we want to delete formulas
            logger.debug("Formulas are going to be deleted.");
            //for canonic outputs having no parent formula
            Set<CanonicOutput> orphaned = new HashSet<>();

            //phase two obtain all parents
            //so duplicates are eliminated easily
            Set<Formula> parentFormulas = new HashSet<>();

            
            // we get prents
            for (CanonicOutput co : canonicOutputs)
            {
                if (co.getParents() != null && !co.getParents().isEmpty())
                {
                    parentFormulas.addAll(co.getParents());
                }
                else
                {   //orphaned one
                    logger.error("Orphaned canonic output with id [" + co.getId() 
                            + "] will be removed manually, not by cascade.");
                    
                    orphaned.add(co);
                }
            }

            // now the issue might be following... one canonic output
            //may have 1 or more parent and it may be moved or original parent
            // is now second this my happen because hibernate does not guarantee
            // the order of the list. so we have to iterate over parents and check
            // if its child belongs to app run.       
            Set<Formula> markedForDelete = new HashSet<>();

            // we refresh canonic outputs
            Set<Formula> parentRefreshed = new HashSet<>();
            for (Formula f : parentFormulas)
            {
                parentRefreshed.add(formulaDAO.getFormulaByID(f.getId()));
            }

            //now begins phase 3 and thats selecting formulas for deletion
            for (Formula f : parentRefreshed)
            {
                if (f.getOutputs() != null && !f.getOutputs().isEmpty())
                {
                    for (CanonicOutput co : f.getOutputs())
                    {
                        if (co.getApplicationRun().equals(applicationRun))
                        {
                            markedForDelete.add(f);
                        }
                    }
                }
            }

            //phase 4 deletes proper formulas
            for (Formula f : markedForDelete)
            {
                formulaDAO.deleteFormula(f);
            }

            //phase 5 remove orphans
            for (CanonicOutput co : orphaned)
            {
                canonicOutputDAO.deleteCanonicOutput(co);
            }

            // once here there are neither canonic outputs nor 
            // formulas belonging to this application run
        }

        // once previous is done there is no reference to apprun
        applicationRunDAO.deleteApplicationRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationRun getApplicationRunByID(Long id)
    {
        return applicationRunDAO.getApplicationRunByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRuns()
    {
        return applicationRunDAO.getAllApplicationRuns();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsByUser(User user)
    {
        return applicationRunDAO.getAllApplicationRunsByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision)
    {
        return applicationRunDAO.getAllApplicationRunsByRevision(revision);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration)
    {
        return applicationRunDAO.getAllApplicationRunsByConfiguration(configuration);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRunsFromRange(int start, int end)
    {
        if (start < 0)
        {
            throw new IllegalArgumentException("ERROR: start cannot be lower than zero. Start value is [" + start + "].");
        }
        else if (end < 0)
        {
            throw new IllegalArgumentException("ERROR: end cannot be lower than zero. End value is [" + start + "].");
        }
        else if (start > end)
        {
            throw new IllegalArgumentException("ERROR: end value cannot be lower than start value. Current value for end is [" + end + "] and for start [" + start + "]");
        }
        else
        {
            return applicationRunDAO.getAllApplicationRunsFromRange(start, end);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteApplicationRunInTask(final ApplicationRun applicationRun,
            boolean deleteFormulas,
            boolean deleteCanonicOutputs)
    {

        // nasty workaround, task created by lookup method
        // did not work.
        
        //does not work either :(
//        taskExecutor.execute(new Runnable()
//        {
//
//            @Override
//            public void run()
//            {
//                deleteApplicationRun(applicationRun, true, true);
//            }
//        });
    }
}
