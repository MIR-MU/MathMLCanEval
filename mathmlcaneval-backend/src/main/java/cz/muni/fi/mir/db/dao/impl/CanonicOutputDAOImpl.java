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
import cz.muni.fi.mir.similarity.SimilarForm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
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
    public List<CanonicOutput> getCanonicOutputBySimilarForm(String form)
    {
        List<CanonicOutput> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT co FROM canonicOutput co WHERE co.similarForm = :similarForm", CanonicOutput.class)
                    .setParameter("similarForm", form).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CanonicOutput> getCanonicOutputByFormula(Formula formula)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CanonicOutput> getCanonicOutputByParentFormula(Formula formula)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CanonicOutput> getSimilarCanonicOutputs(CanonicOutput canonicOutput)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
