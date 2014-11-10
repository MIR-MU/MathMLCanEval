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
package cz.muni.fi.mir.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.service.AnnotationService;

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
    @Transactional(readOnly = false)
    public void createAnnotation(Annotation annotation) throws IllegalArgumentException
    {
        if(annotation == null)
        {
            throw new IllegalArgumentException("Given annotation is null.");
        }
        
        annotationDAO.create(annotation);
    }

    @Override    
    public Annotation getAnnotationByID(Long id) throws IllegalArgumentException
    {
        if(id == null)
        {
            throw new IllegalArgumentException("Given id is null");
        }
        if(Long.valueOf("0").compareTo(id) >= 0)
        {
            throw new IllegalArgumentException("Given id is out of valid range. Should be greater than 0 and is ["+id+"]");
        }
        
        return annotationDAO.getByID(id);
    }
}
