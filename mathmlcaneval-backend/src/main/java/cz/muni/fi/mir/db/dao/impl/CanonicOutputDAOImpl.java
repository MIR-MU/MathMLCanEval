/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "canonicOutputDAO")
public class CanonicOutputDAOImpl implements CanonicOutputDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CanonicOutputDAOImpl.class);

    @Override
    public void createCanonicOutput(CanonicOutput canonicOutput)
    {
        entityManager.persist(canonicOutput);
    }

    @Override
    public void updateCanonicOutput(CanonicOutput canonicOutput)
    {
        entityManager.merge(canonicOutput);
    }

    @Override
    public void deleteCanonicOutput(CanonicOutput canonicOutput)
    {
        CanonicOutput co = entityManager.find(CanonicOutput.class, canonicOutput.getId());
        if(co != null)
        {
            entityManager.remove(co);
        }
        else
        {
            logger.info("Trying to delete CanonicOutput with ID that has not been found. The ID is ["+canonicOutput.getId().toString()+"]");
        }
    }

    @Override
    public CanonicOutput getCanonicOutputByID(Long id)
    {
        return entityManager.find(CanonicOutput.class, id);
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
}
