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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cz.muni.fi.mir.db.dao.ApplicationRunDAO;
import cz.muni.fi.mir.db.domain.ApplicationRun;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository(value = "applicationRunDAO")
public class ApplicationRunDAOImpl extends GenericDAOImpl<ApplicationRun, Long> implements ApplicationRunDAO
{
    private static final Logger logger = Logger.getLogger(ApplicationRunDAOImpl.class);   
    
    public ApplicationRunDAOImpl()
    {
        super(ApplicationRun.class);
    }

    @Override
    public void createApplicationRunWithFlush(ApplicationRun applicationRun)
    {
        super.create(applicationRun);
        logger.info("Attempt to flush");
        entityManager.flush();
    }
    
    @Override
    public Integer getNumberOfCanonicalizations(ApplicationRun applicationRun)
    {
        int count = 0;
        try
        {
            count = entityManager.createQuery("SELECT count(co) FROM canonicOutput co WHERE co.applicationRun = :apprun", Long.class)
                    .setParameter("apprun", applicationRun).getSingleResult().intValue();
        }
        catch (NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return count;
    }
    
    @Override
    public List<ApplicationRun> getAllApplicationRunsFromRange(int start, int end)
    {
        List<ApplicationRun> resultList = new ArrayList<>();

        // if there are no results yet (count is 0) do nothing and return empty resultList
        // otherwise jump in condition and do the select
        if (!entityManager.createQuery("SELECT COUNT(ar) FROM applicationRun ar", Long.class)
                .getSingleResult().equals(Long.valueOf("0")))
        {            
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr ORDER BY apr.id DESC", ApplicationRun.class)
                    .setFirstResult(start).setMaxResults(end-start).getResultList();

            for(ApplicationRun ar : resultList)
            {
                Long count = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co WHERE co.applicationRun = :apprun", Long.class)
                        .setParameter("apprun", ar)                        
                        .getSingleResult();
                ar.setCanonicOutputCount(count.intValue());
            }            
        }
        
        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRuns()
    {
        return entityManager.createQuery("SELECT apr FROM applicationRun apr ORDER BY apr.id DESC", ApplicationRun.class).getResultList();
    }
}
