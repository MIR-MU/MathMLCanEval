/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service.impl;

import cz.muni.fi.mir.dao.AnnotationFlagDAO;
import cz.muni.fi.mir.domain.AnnotationFlag;
import cz.muni.fi.mir.service.AnnotationFlagService;
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
}
