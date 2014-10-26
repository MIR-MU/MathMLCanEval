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
package cz.muni.fi.mir.db.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import cz.muni.fi.mir.db.dao.AnnotationDAO;
import cz.muni.fi.mir.db.domain.Annotation;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository(value = "annotationDAO")
public class AnnotationDAOImpl extends GenericDAOImpl<Annotation, Long> implements AnnotationDAO
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationDAOImpl.class);

    public AnnotationDAOImpl()
    {
        super(Annotation.class);
    }

    @Override
    public List<Annotation> getAllAnnotations()
    {
        List<Annotation> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT a FROM annotation a ORDER BY a.id DESC", Annotation.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
