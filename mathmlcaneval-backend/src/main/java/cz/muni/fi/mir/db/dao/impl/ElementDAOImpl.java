/*
 * Copyright 2014 emptak.
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

import cz.muni.fi.mir.db.dao.ElementDAO;
import cz.muni.fi.mir.db.domain.Element;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emptak
 */
@Repository(value = "elementDAO")
public class ElementDAOImpl implements ElementDAO
{
    private static final Logger logger = Logger.getLogger(AnnotationDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createElement(Element element)
    {
        entityManager.persist(element);
    }

    @Override
    public void updateElement(Element element)
    {
        entityManager.merge(element);
    }

    @Override
    public void deleteElement(Element element)
    {
        Element e = entityManager.find(Element.class, element.getId());
        if(e != null)
        {
            entityManager.remove(e);
        }
    }

    @Override
    public Element getElementByID(Long id)
    {
        return entityManager.find(Element.class, id);
    }

    @Override
    public Element getElementByName(String name)
    {
        Element e = null;
        try
        {
            e = entityManager.createQuery("SELECT e FROM element e WHERE e.elementName = :elementName", Element.class)
                    .setParameter("elementName", name).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return e;
    }

    @Override
    public List<Element> getAllElements()
    {
        List<Element> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT e FROM element e", Element.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
    
}
