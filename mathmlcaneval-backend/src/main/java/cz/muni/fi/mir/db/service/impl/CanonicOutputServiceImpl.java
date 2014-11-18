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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.SearchResponse;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.tools.IndexTools;
import cz.muni.fi.mir.tools.Tools;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Service(value = "canonicOutputService")
@Transactional(readOnly = false)
public class CanonicOutputServiceImpl implements CanonicOutputService
{
    private static final Logger logger = Logger.getLogger(CanonicOutputServiceImpl.class);
    @Value("${hibernate.jdbc.batch_size}")
    private Integer batchSize;
    @Autowired
    private CanonicOutputDAO canonicOutputDAO;
    @Autowired
    private AnnotationDAO annotationDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private IndexTools indexTools;

    @Override
    public void createCanonicOutput(CanonicOutput canonicOutput) throws IllegalArgumentException
    {
        if(canonicOutput == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        
        canonicOutputDAO.create(canonicOutput);
    }

    @Override
    public void deleteCanonicOutput(CanonicOutput canonicOutput) throws IllegalArgumentException
    {
        InputChecker.checkInput(canonicOutput);
        
        canonicOutputDAO.delete(canonicOutput.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput getCanonicOutputByID(Long id) throws IllegalArgumentException
    {
        if(Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+id+"]");
        }
        
        return canonicOutputDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput getCanonicOutputByAnnotation(Annotation annotation) throws IllegalArgumentException
    {
        return canonicOutputDAO.getCanonicOutputByAnnotation(annotation);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResponse<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun, Pagination pagination) throws IllegalArgumentException
    {
        return canonicOutputDAO.getCanonicOutputByAppRun(applicationRun, pagination);
    }

    @Override
    public void annotateCannonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException
    {
        InputChecker.checkInput(canonicOutput);
        if(annotation == null)
        {
            throw new IllegalArgumentException("Given annotation is null");
        }
        
        List<Annotation> temp = new ArrayList<>();
        if(canonicOutput.getAnnotations() != null && !canonicOutput.getAnnotations().isEmpty())
        {
            temp.addAll(canonicOutput.getAnnotations());
        }
        annotationDAO.create(annotation);
        
        temp.add(annotation);
        
        canonicOutput.setAnnotations(temp);
        
        canonicOutputDAO.update(canonicOutput);     
        
        indexTools.index(formulaDAO.getFormulaByCanonicOutput(canonicOutput));
    }

    @Override
    public void deleteAnnotationFromCanonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException
    {
        InputChecker.checkInput(canonicOutput);
        InputChecker.checkInput(annotation);
        
        List<Annotation> temp = new ArrayList<>(canonicOutput.getAnnotations());

        temp.remove(annotation);

        canonicOutput.setAnnotations(temp);

        canonicOutputDAO.update(canonicOutput);

        annotationDAO.delete(annotation.getId());
    }

    @Override
    public void updateCanonicOutput(CanonicOutput canonicOutput) throws IllegalArgumentException
    {
        canonicOutputDAO.update(canonicOutput);
    }

    @Override
    @Transactional(readOnly = false)
    public void recalculateHashes()
    {
        // TODO @RobSis pagination maybe ? :)
        int totalCo = canonicOutputDAO.getNumberOfCanonicOutputs();
        int calls = totalCo / batchSize;
        
        for(int i = 0; i < calls+1;i++)
        {
            logger.info(i*batchSize+"$"+((i+1)*batchSize));
            List<CanonicOutput> list = canonicOutputDAO.getSubListOfOutputs(i*batchSize, (i+1)*batchSize);
            
            for(CanonicOutput co : list)
            {
                co.setHashValue(Tools.getInstance().SHA1(co.getOutputForm()));
                
                canonicOutputDAO.update(co);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput nextInRun(CanonicOutput current) throws IllegalArgumentException
    {
        InputChecker.checkInput(current);
        
        return canonicOutputDAO.nextInRun(canonicOutputDAO.getByID(current.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput previousInRun(CanonicOutput current) throws IllegalArgumentException
    {
        InputChecker.checkInput(current);
        
        return canonicOutputDAO.previousInRun(canonicOutputDAO.getByID(current.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput firstInRun(CanonicOutput current) throws IllegalArgumentException
    {
        InputChecker.checkInput(current);
        
        return canonicOutputDAO.firstInRun(canonicOutputDAO.getByID(current.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput lastInRun(CanonicOutput current) throws IllegalArgumentException
    {
        InputChecker.checkInput(current);
        
        return canonicOutputDAO.lastInRun(canonicOutputDAO.getByID(current.getId()));
    }
}
