/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlevaluation.db.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.mathmlevaluation.db.dao.AnnotationDAO;
import cz.muni.fi.mir.mathmlevaluation.db.dao.AnnotationValueDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Annotation;
import cz.muni.fi.mir.mathmlevaluation.db.domain.AnnotationValue;
import cz.muni.fi.mir.mathmlevaluation.db.service.AnnotationValueSerivce;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
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
    public void createAnnotationValue(AnnotationValue annotationValue) throws IllegalArgumentException
    {
        if(annotationValue == null)
        {
            throw new IllegalArgumentException("Given annotaion value is null");
        }
        
        annotationValueDAO.create(annotationValue);
    }

    @Override
    public void updateAnnotationValue(AnnotationValue annotationValue) throws IllegalArgumentException
    {
        InputChecker.checkInput(annotationValue);
        
        annotationValueDAO.update(annotationValue);
    }

    @Override
    public void deleteAnnotationValue(AnnotationValue annotationValue) throws IllegalArgumentException
    {
        InputChecker.checkInput(annotationValue);
        
        annotationValueDAO.delete(annotationValue.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public AnnotationValue getAnnotationValueByID(Long id) throws IllegalArgumentException
    {
        if (id == null || Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given annotation value does not have valid id should be greater than one but was [" + id + "]");
        }            
            
        return annotationValueDAO.getByID(id);
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

    @Override
    @Transactional(readOnly = true)
    public AnnotationValue getAnnotationValueByValue(String value) throws IllegalArgumentException
    {
        if(value == null || value.length() < 1)
        {
            throw new IllegalArgumentException("Given input for annotation value should be empty. Expected nonzero length string but was ["+value+"]");
        }
        
        return annotationValueDAO.getByValue(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnotationValue> getAllForFormulas()
    {
        return annotationValueDAO.getAllForFormulas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnotationValue> getAllForCanonicOutputs()
    {
        return annotationValueDAO.getAllForCanonicOutputs();
    }    
}
