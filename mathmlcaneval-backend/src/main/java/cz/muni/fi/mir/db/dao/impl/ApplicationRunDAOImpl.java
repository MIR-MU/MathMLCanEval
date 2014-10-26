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
import org.springframework.beans.factory.annotation.Value;
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

    /**
     * Postgreqsql version. If value is set to 9.1 or higher then {@link #getAllApplicationRuns()
     * } method has different behaviour.
     */
    @Value("${postgresql.version}")
    private String psqlver;
    private static final Logger logger = Logger.getLogger(ApplicationRunDAOImpl.class);   
    
    public ApplicationRunDAOImpl()
    {
        super(ApplicationRun.class);
    }

    /**
     * Method is affected by {@link #psqlver }. If set to
     * version (>= 9.1) then method uses calculation of outputs in single
     * method. Otherwise two selects are required as of bug in previous versions
     * of postgresql. See <a
     * href="http://stackoverflow.com/a/11683182/1203690">this</a> for more
     * details.
     *
     * @return List of application runs.
     */
    @Override
    public List<ApplicationRun> getAllApplicationRuns()
    {
        List<ApplicationRun> resultList = new ArrayList<>();

        // if there are no results yet (count is 0) do nothing and return empty resultList
        // otherwise jump in condition and do the select
        if (!entityManager.createQuery("SELECT COUNT(ar) FROM applicationRun ar", Long.class)
                .getSingleResult().equals(Long.valueOf("0")))
        {
            if(psqlver.equals("9") || psqlver.equals("none"))
            {
                resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr", ApplicationRun.class).getResultList();
            
                for(ApplicationRun ar : resultList)
                {
                    Long count = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co WHERE co.applicationRun = :apprun", Long.class)
                            .setParameter("apprun", ar).getSingleResult();
                    ar.setCanonicOutputCount(count.intValue());
                }
            }
            else
            {
                List<Object[]> results
                        = entityManager.createQuery("SELECT apr,COUNT(co) FROM applicationRun apr LEFT JOIN FETCH apr.configuration, canonicOutput co WHERE co.applicationRun = apr GROUP BY apr.id")
                        .getResultList();

                for (Object[] row : results)
                {
                    ApplicationRun ar = (ApplicationRun) row[0];
                    Long count = (Long) row[1];

                    ar.setCanonicOutputCount(count.intValue());

                    resultList.add(ar);
                }
            }

        }

        return resultList;
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
}
