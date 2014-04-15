/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
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
@Repository(value = "formulaDAO")
public class FormulaDAOImpl implements FormulaDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FormulaDAOImpl.class);
    
    @Override
    public void createFormula(Formula formula)
    {
        entityManager.persist(formula);
    }

    @Override
    public void updateFormula(Formula formula)
    {
        entityManager.merge(formula);
    }

    @Override
    public void deleteFormula(Formula formula)
    {
        Formula m = entityManager.find(Formula.class, formula.getId());
        if(m != null)
        {
            entityManager.remove(m);
        }
        else
        {
            logger.info("Trying to delete Formula with ID that has not been found. The ID is ["+formula.getId().toString()+"]");
        }
    }

    @Override
    public Formula getFormulaByID(Long id)
    {
        return entityManager.find(Formula.class, id);
    }

    @Override
    public List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.sourceDocument = :sourceDocument", Formula.class)
                    .setParameter("sourceDocument", sourceDocument).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
    @Override
    public List<Formula> getFormulasByProgram(Program program)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.program = :program", Formula.class)
                    .setParameter("program", program).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Formula> getFormulasByUser(User user)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f WHERE f.user = :user", Formula.class)
                    .setParameter("user", user).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }

    @Override
    public List<Formula> getAllFormulas()
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f ORDER BY id DESC", Formula.class).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }

        return resultList;
    }
 
    @Override
    public List<Formula> getAllFormulas(int skip, int number)
    {
        List<Formula> resultList = Collections.emptyList();
        try
        {
            resultList = entityManager.createQuery("SELECT f FROM formula f ORDER BY id DESC", Formula.class)
                    .setFirstResult(skip)
                    .setMaxResults(number)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
