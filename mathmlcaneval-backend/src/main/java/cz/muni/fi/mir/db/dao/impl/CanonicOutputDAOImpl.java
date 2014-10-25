/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "canonicOutputDAO")
public class CanonicOutputDAOImpl extends GenericDAOImpl<CanonicOutput, Long> implements CanonicOutputDAO
{  
    private static final Logger logger = Logger.getLogger(CanonicOutputDAOImpl.class);
    
    public CanonicOutputDAOImpl()
    {
        super(CanonicOutput.class);
    }

    @Override
    public List<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun)
    {
        List<CanonicOutput> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.applicationRun = :appRun", CanonicOutput.class)
                    .setParameter("appRun", applicationRun).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;       
    }

    @Override
    public List<CanonicOutput> getCanonicOutputByFormula(Formula formula)
    {
        List<CanonicOutput> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f JOIN FETCH f.outputs WHERE f.id = :formulaID", Formula.class)
                    .setParameter("formulaID", formula.getId()).getSingleResult().getOutputs();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<CanonicOutput> getCanonicOutputByParentFormula(Formula formula)
    {
        List<CanonicOutput> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE :formulaID MEMBER OF co.parents", CanonicOutput.class)
                    .setParameter("formulaID", formula.getId()).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public CanonicOutput getCanonicOutputByAnnotation(Annotation annotation)
    {
        CanonicOutput result = null;

        try
        {
            result = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE :annotationID MEMBER OF co.annotations", CanonicOutput.class)
                    .setParameter("annotationID", annotation.getId()).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        return result;
    }
}
