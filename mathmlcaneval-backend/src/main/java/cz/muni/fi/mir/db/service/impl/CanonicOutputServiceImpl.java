/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
@Service(value = "canonicOutputService")
@Transactional(readOnly = false)
public class CanonicOutputServiceImpl implements CanonicOutputService
{

    @Autowired
    private CanonicOutputDAO canonicOutputDAO;
    @Autowired
    private AnnotationDAO annotationDAO;
    @Autowired
    private FormulaDAO formulaDAO;

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
        checkInput(canonicOutput);
        
        canonicOutputDAO.delete(canonicOutput.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public CanonicOutput getCanonicOutputByID(Long id) throws IllegalArgumentException
    {
        if(Long.valueOf("0").compareTo(id) < 1)
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
    public void annotateCannonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException
    {
        checkInput(canonicOutput);
        checkInput(annotation);        
        
        List<Annotation> temp = new ArrayList<>();
        if(canonicOutput.getAnnotations() != null && !canonicOutput.getAnnotations().isEmpty())
        {
            temp.addAll(canonicOutput.getAnnotations());
        }
        annotationDAO.create(annotation);
        
        temp.add(annotation);
        
        canonicOutput.setAnnotations(temp);
        
        canonicOutputDAO.update(canonicOutput);     
        
        formulaDAO.index(formulaDAO.getFormulaByCanonicOutput(canonicOutput));
    }

    @Override
    public void deleteAnnotationFromCanonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException
    {
        checkInput(canonicOutput);
        checkInput(annotation);
        
        List<Annotation> temp = new ArrayList<>(canonicOutput.getAnnotations());

        temp.remove(annotation);

        canonicOutput.setAnnotations(temp);

        canonicOutputDAO.update(canonicOutput);

        annotationDAO.delete(annotation.getId());
    }
    
    /**
     * Method checks given canonic output on input.
     * @param canonicOutput to be checked
     * @throws IllegalArgumentException if canonic output is null, or does not have valid id.
     */
    private void checkInput(CanonicOutput canonicOutput) throws IllegalArgumentException
    {
        if(canonicOutput == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        if(canonicOutput.getId() == null || Long.valueOf("0").compareTo(canonicOutput.getId()) < 1)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+canonicOutput.getId()+"]");
        }
    }
    
    /**
     * Method checks annotation on input.
     * @param annotation to be checked
     * @throws IllegalArgumentException if canonic output is null, or does not have valid id.
     */
    private void checkInput(Annotation annotation) throws IllegalArgumentException
    {
        if(annotation == null)
        {
            throw new IllegalArgumentException("Given input is null.");
        }
        if(annotation.getId() == null || Long.valueOf("0").compareTo(annotation.getId()) < 1)
        {
            throw new IllegalArgumentException("Given entity does not have valid id should be greater than one but was ["+annotation.getId()+"]");
        }
    }
}
