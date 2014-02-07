/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
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
    public List<Formula> getFormulasBySourceDocument(SourceDocument sourceDocument)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public List<Formula> getFormulasByProgram(Program program)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Formula> getFormulasByUser(User user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Formula> getFormulasBySimilarOutput(CanonicOutput canonicOutput)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
