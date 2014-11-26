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
package cz.muni.fi.mir.db.audit;

import cz.muni.fi.mir.db.domain.SearchResponse;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */

@Service
@Transactional(readOnly = true)
public class AuditorServiceImpl implements AuditorService
{
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(AuditorServiceImpl.class);
    
    @Override
    @Transactional(readOnly = false)
    public void createDatabaseEvent(DatabaseEvent databaseEvent)
    {        
        entityManager.persist(databaseEvent);
    }

    @Override
    public List<DatabaseEvent> getLatestEvents()
    {
        List<DatabaseEvent> result = new ArrayList<>();
        
        try
        {
            result = entityManager.createQuery("SELECT de FROM databaseEvent de ORDER BY de.id DESC", DatabaseEvent.class)
                    .setMaxResults(40).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return result;
    }

    @Override
    public SearchResponse<DatabaseEvent> getLatestEvents(String user, String event, String keyword, int start, int end)
    {
        Query q = entityManager.createQuery("SELECT de FROM databaseEvent de WHERE de.message LIKE :message")
                .setParameter("message", "%"+keyword+"%").setFirstResult(start)
                .setMaxResults(end-start);
        
        Long count = entityManager.createQuery("SELECT count(de) FROM databaseEvent de WHERE de.message LIKE :message", Long.class)
                .setParameter("message", "%"+keyword+"%").getSingleResult();
        
        
        SearchResponse<DatabaseEvent> response = new SearchResponse<>();
        response.setResults(q.getResultList());
        response.setViewSize(count.intValue());
        
        
        return response;
    }

    @Override
    public Long getNumberOfEvents()
    {
        Long result = null;
        
        try
        {
            result = entityManager.createQuery("SELECT count(de) FROM databaseEvent de", Long.class)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            
        }
        
        if(result == null)
        {
            return Long.valueOf("-1");
        }
        else
        {
            return result;
        }
    }
}