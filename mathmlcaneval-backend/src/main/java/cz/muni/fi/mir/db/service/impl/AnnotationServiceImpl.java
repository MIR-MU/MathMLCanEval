/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.AnnotationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "annotationService")
public class AnnotationServiceImpl implements AnnotationService
{
    @Autowired private AnnotationDAO annotationDAO;    
    
    @Override
    @Transactional(readOnly = false)
    public void createAnnotation(Annotation annotation)
    {
        annotationDAO.create(annotation);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateAnnotation(Annotation annotation)
    {
        annotationDAO.update(annotation);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAnnotation(Annotation annotation)
    {
        annotationDAO.delete(annotation.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Annotation getAnnotationByID(Long id)
    {
        return annotationDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> getAllAnnotations()
    {
        return annotationDAO.getAllAnnotations();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> getAnnotationByUser(User user)
    {
        return annotationDAO.getAnnotationByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> findByNote(String note)
    {
        return annotationDAO.findByNote(note);
    }    

    @Override
    @Transactional(readOnly = true)
    public List<Annotation> getAllAnnotationsFromRange(int start, int end)
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
            return annotationDAO.getAllAnnotationsFromRange(start, end);
        }        
    }
}
