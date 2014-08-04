/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "canonicOutputService")
public class CanonicOutputServiceImpl implements CanonicOutputService
{

    @Autowired
    private CanonicOutputDAO canonicOutputDAO;

    @Override
    @Transactional(readOnly = false)
    public void createCanonicOutput(CanonicOutput canonicOutput)
    {
        canonicOutputDAO.createCanonicOutput(canonicOutput);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCanonicOutput(CanonicOutput canonicOutput)
    {
        canonicOutputDAO.updateCanonicOutput(canonicOutput);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCanonicOutput(CanonicOutput canonicOutput)
    {
        canonicOutputDAO.deleteCanonicOutput(canonicOutput);
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput getCanonicOutputByID(Long id)
    {
        return canonicOutputDAO.getCanonicOutputByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun)
    {
        return canonicOutputDAO.getCanonicOutputByAppRun(applicationRun);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanonicOutput> getCanonicOutputByFormula(Formula formula)
    {
        return canonicOutputDAO.getCanonicOutputByFormula(formula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanonicOutput> getCanonicOutputByParentFormula(Formula formula)
    {
        return canonicOutputDAO.getCanonicOutputByParentFormula(formula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanonicOutput> getSimilarCanonicOutputs(CanonicOutput canonicOutput, int skip, int maxResults)
    {
        return canonicOutputDAO.getSimilarCanonicOutputs(canonicOutput, skip, maxResults);
    }
}
