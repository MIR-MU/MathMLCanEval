/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.AnnotationFlagDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.service.AnnotationFlagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "annotationFlagService")
public class AnnotationFlagServiceImpl implements AnnotationFlagService
{
    @Autowired private AnnotationFlagDAO annotationFlagDAO;

    @Override
    @Transactional(readOnly = false)
    public void createFlagAnnotation(AnnotationFlag annotationFlag)
    {
        annotationFlagDAO.createFlagAnnotation(annotationFlag);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateFlagAnnotation(AnnotationFlag annotationFlag)
    {
        annotationFlagDAO.updateFlagAnnotation(annotationFlag);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteFlagAnnotation(AnnotationFlag annotationFlag)
    {
        annotationFlagDAO.deleteFlagAnnotation(annotationFlag);
    }

    @Override
    @Transactional(readOnly = true)
    public AnnotationFlag getAnnotationFlagByID(Long id)
    {
        return annotationFlagDAO.getAnnotationFlagByID(id);
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<AnnotationFlag> getAllAnnotationFlags()
    {
        return annotationFlagDAO.getAllAnnotationFlags();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnotationFlag> findAnnotationFlagByValue(String value)
    {
        return annotationFlagDAO.findAnnotationFlagByValue(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnotationFlag> getAllAnnotationFlagsFromRange(int start, int end)
    {
        if(start < 0)
        {
            throw new IllegalArgumentException("ERROR: start cannot be lower than zero. Start value is ["+start+"].");
        }
        else if(end < 0)
        {
            throw new IllegalArgumentException("ERROR: end cannot be lower than zero. End value is ["+start+"].");
        }
        else if(start > end)
        {
            throw new IllegalArgumentException("ERROR: end value cannot be lower than start value. Current value for end is ["+end+"] and for start ["+start+"]");
        }
        else
        {
            return annotationFlagDAO.getAllAnnotationFlagsFromRange(start, end);
        }
    }
}
