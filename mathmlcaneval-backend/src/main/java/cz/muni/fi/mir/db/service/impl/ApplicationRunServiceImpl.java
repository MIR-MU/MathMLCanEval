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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.ApplicationRunService;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Service(value = "applicationRunService")
@Transactional(readOnly = false)
public class ApplicationRunServiceImpl implements ApplicationRunService
{
    private static final Logger logger = Logger.getLogger(ApplicationRunServiceImpl.class);

    @Autowired
    private ApplicationRunDAO applicationRunDAO;
    @Autowired
    private CanonicOutputDAO canonicOutputDAO;
    @Autowired
    private FormulaDAO formulaDAO;

    @Override
    public void createApplicationRun(ApplicationRun applicationRun,boolean withFlush) throws IllegalArgumentException
    {
        if(applicationRun == null)
        {
            throw new IllegalArgumentException("Given application run is null.");
        }
        
        if(withFlush)
        {
            applicationRunDAO.createApplicationRunWithFlush(applicationRun);
        }
        else
        {
            applicationRunDAO.create(applicationRun);
        }        
    }

    @Override
    public void updateApplicationRun(ApplicationRun applicationRun) throws IllegalArgumentException
    {
        InputChecker.checkInput(applicationRun);
        
        applicationRunDAO.update(applicationRun);
    }

    @Override
    public void deleteApplicationRun(ApplicationRun applicationRun,
            boolean deleteFormulas,
            boolean deleteCanonicOutputs) throws IllegalArgumentException
    {
        InputChecker.checkInput(applicationRun);
        
        //phase one obtain all outputs that belongs to run
        List<CanonicOutput> canonicOutputs = canonicOutputDAO.getCanonicOutputByAppRun(applicationRun).getResults();

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
                    canonicOutputDAO.delete(co.getId());
                }
                else
                {   //otherwise we remove reference to apprun and update it
                    co.setApplicationRun(null);
                    canonicOutputDAO.update(co);
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
                canonicOutputDAO.delete(co.getId());
            }

            // once here there are neither canonic outputs nor 
            // formulas belonging to this application run
        }

        // once previous is done there is no reference to apprun
        applicationRunDAO.delete(applicationRun.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationRun getApplicationRunByID(Long id) throws IllegalArgumentException
    {
        if(id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Invalid entity id should be greater than 0 but was ["+id+"]");
        }
        
        return applicationRunDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRun> getAllApplicationRuns() 
    {
        return applicationRunDAO.getAllApplicationRuns();
    }
}
