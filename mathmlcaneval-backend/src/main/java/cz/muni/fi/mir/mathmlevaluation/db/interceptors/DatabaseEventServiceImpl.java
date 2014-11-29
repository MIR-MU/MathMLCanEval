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
package cz.muni.fi.mir.mathmlevaluation.db.interceptors;

import cz.muni.fi.mir.mathmlevaluation.db.domain.SearchResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */

@Service
@Transactional(readOnly = true)
public class DatabaseEventServiceImpl implements DatabaseEventService
{
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(DatabaseEventServiceImpl.class);
    private static final String USER_PREFIX = "u:";
    private static final String OPERATION_PREFIX = "o:";
    private static final String TARGET_CLASS_PREFIX = "c:";
    
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
        SearchResponse<DatabaseEvent> response = new SearchResponse<>();
        if(keyword == null || keyword.length() < 3)
        {
            List<DatabaseEvent> resultList = entityManager.createQuery("SELECT de FROM databaseEvent de ORDER BY de.id DESC", DatabaseEvent.class)
                    .setFirstResult(start).setMaxResults(end).getResultList();
            response.setResults(resultList);
            response.setViewSize(getNumberOfEvents().intValue());
        }
        else
        {
            String[] keywordsSplit = keyword.split(" ");
            String username = null;
            String operation = null;
            String targetClass = null;
            Set<String> keywords = new HashSet<>();
            
            for(String s : keywordsSplit)
            {
                String trimmed = StringUtils.trimToEmpty(s);
                if(trimmed.length() > 0)
                {
                    if(trimmed.startsWith(USER_PREFIX))
                    {
                        username = StringUtils.substringAfter(trimmed, USER_PREFIX);
                    }
                    else if(trimmed.startsWith(OPERATION_PREFIX))
                    {
                        operation = StringUtils.substringAfter(trimmed, OPERATION_PREFIX);
                    }
                    else if(trimmed.startsWith(TARGET_CLASS_PREFIX))
                    {
                        targetClass = StringUtils.substringAfter(trimmed, TARGET_CLASS_PREFIX);
                    }
                    else
                    {
                        keywords.add(trimmed);                                          
                    }
                }                
            }
            
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            org.hibernate.search.jpa.FullTextQuery ftq = null;

            QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(DatabaseEvent.class)
                    .overridesForField("message", "databaseEventAnalyzerQuery")
                    .get();
        
            Query query = null;
            if(username != null || operation != null || targetClass != null)
            {
                BooleanJunction<BooleanJunction> junction = qb.bool();
                
                if(username != null)
                {
                    junction.must(qb.keyword().onField("user.username").matching(username).createQuery());
                }
                if(operation != null)
                {
                    junction.must(qb.keyword().onField("operation").matching(DatabaseEvent.Operation.valueOf(operation.toUpperCase())).createQuery());
                }
                if(targetClass != null)
                {
                    junction.must(qb.keyword().onField("targetClass").matching(targetClass).createQuery());                    
                }
                
                if(!keywords.isEmpty())
                {
                    StringBuilder sb = new StringBuilder();
                    for(String s : keywords)
                    {
                        sb.append(s).append(" ");
                    }

                    junction.must(qb.keyword().onField("message").matching(sb.toString()).createQuery());
                }                
                
                query = junction.createQuery();
            }
            else
            {
                query = qb.keyword().onField("message").matching(keyword).createQuery();
            }
            
            logger.info("Minifeed Lucene query: "+query);
            
            ftq = fullTextEntityManager.createFullTextQuery(query, DatabaseEvent.class);

            response.setViewSize(ftq.getResultSize());
            response.setResults(ftq.setFirstResult(start).setMaxResults(end-start).getResultList());
        }
        
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