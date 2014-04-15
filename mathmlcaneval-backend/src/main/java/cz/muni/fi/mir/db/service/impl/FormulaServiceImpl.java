/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import java.util.List;
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
    @Autowired FormulaDAO formulaDAO;

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
}
