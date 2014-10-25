/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.dao.AnnotationValueDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationValue;
import cz.muni.fi.mir.db.service.AnnotationValueSerivce;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class AnnotationValueSerivceImpl implements AnnotationValueSerivce
{
    @Autowired
    private AnnotationValueDAO annotationValueDAO;
    @Autowired
    private AnnotationDAO annotationDAO;
    private final Pattern pattern = Pattern.compile("(#\\S+)");

    @Override
    public void createAnnotationValue(AnnotationValue annotationValue)
    {
        annotationValueDAO.create(annotationValue);
    }

    @Override
    public void updateAnnotationValue(AnnotationValue annotationValue)
    {
        annotationValueDAO.update(annotationValue);
    }

    @Override
    public void deleteAnnotationValue(AnnotationValue annotationValue)
    {
        annotationValueDAO.delete(annotationValue.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public AnnotationValue getAnnotationValueByID(AnnotationValue annotationValue)
    {
        return annotationValueDAO.getByID(annotationValue.getId());
    }

    @Override
    public void populate()
    {
        List<Annotation> annotations = annotationDAO.getAllAnnotations();
        Set<String> annotationsValues = new HashSet<>();
        
        Matcher m = pattern.matcher("");
        for(Annotation a : annotations)
        {
            m.reset(a.getAnnotationContent());
            
            while(m.find())
            {
                annotationsValues.add(m.group());
            }
        }
        
        for(String s : annotationsValues)
        {
            AnnotationValue av = annotationValueDAO.getByValue(s);
            if(av == null)
            {
                av = new AnnotationValue();
                av.setValue(s);
                annotationValueDAO.create(av);
            }
        }       
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnotationValue> getAll()
    {
        return annotationValueDAO.getAll();
    }
    
}
