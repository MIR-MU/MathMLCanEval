/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.service.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "annotationService")
@Transactional(readOnly = true)
public class AnnotationServiceImpl implements AnnotationService
{
    @Autowired 
    private AnnotationDAO annotationDAO;        

    @Override    
    public Annotation getAnnotationByID(Long id) throws IllegalArgumentException
    {
        if(id == null)
        {
            throw new IllegalArgumentException("Given id is null");
        }
        if(Long.valueOf("0").compareTo(id) < 1)
        {
            throw new IllegalArgumentException("Given id is out of valid range. Should be greater than 0 and is ["+id+"]");
        }
        
        return annotationDAO.getByID(id);
    }
}
