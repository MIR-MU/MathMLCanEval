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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.domain.Statistics;
import cz.muni.fi.mir.db.service.StatisticsService;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class StatisticsServiceImpl implements StatisticsService
{
    private String coIsValid;
    private String coIsInvalid;
    private String coUncertain;
    private String coRemove;
    private String formulaMeaningless;
    private String formulaRemove;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class);
    
    @Override
    @Transactional(readOnly = false)
    public void calculate()
    {
        Statistics statistics = new Statistics();
        
        calc(statistics);
        entityManager.persist(statistics);        
    }
    
    @Scheduled(cron = "${statistics.generate.cron}" )
    @Transactional(readOnly = false)
    public void scheduledCalculation()
    {
        calculate();
    }
    
    
    private void calc(Statistics statistics)
    {
        statistics.setCalculationDate(DateTime.now());
        Long totalFormulas = null;
        Long totalCanonicalized = null;
        Long totalValid = null;
        Long totalInvalid = null;
        Long totalUncertain = null;
        Long totalRemove = null ;
        Long totalFormulasWithCO = null;
        Long totalFormulaRemove = null;
        Long totalFormulaMeaningless = null;
        
        try
        {
            totalFormulas = entityManager.createQuery("SELECT COUNT(f) FROM formula f", Long.class).getSingleResult();
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        
        try
        {
            totalValid = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.annotationContent LIKE :is_valid",Long.class)
                    .setParameter("is_valid", "%"+coIsValid+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalInvalid = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.annotationContent LIKE :is_invalid",Long.class)
                    .setParameter("is_invalid", "%"+coIsInvalid+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalUncertain = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.annotationContent LIKE :is_uncertain",Long.class)
                    .setParameter("is_uncertain", "%"+coUncertain+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalRemove = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.annotationContent LIKE :toremove",Long.class)
                    .setParameter("toremove", "%"+coRemove+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalCanonicalized = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co",Long.class)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.error(nre);
        }
        
        try
        {
            totalFormulasWithCO = entityManager.createQuery("SELECT COUNT(f) FROM formula f WHERE f.outputs IS NOT EMPTY", Long.class)
                    .getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.error(nre);
        }
        
        try
        {
            totalFormulaRemove = entityManager.createQuery("SELECT COUNT(f) FROM formula f JOIN f.annotations fa WHERE fa.annotationContent LIKE :fRemove", Long.class)
                    .setParameter("fRemove", "%"+formulaRemove+"%").getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.error(nre);
        }
        
        try
        {
            totalFormulaMeaningless = entityManager.createQuery("SELECT COUNT(f) FROM formula f JOIN f.annotations fa WHERE fa.annotationContent LIKE :fMeaningles", Long.class)
                    .setParameter("fMeaningles", "%"+formulaMeaningless+"%").getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.error(nre);
        }
        
        
        if(totalFormulas != null)
        {
            statistics.setTotalFormulas(totalFormulas.intValue());
        }
        if(totalValid != null)
        {
            statistics.setTotalValid(totalValid.intValue());
        }
        if(totalInvalid != null)
        {
            statistics.setTotalInvalid(totalInvalid.intValue());
        }
        if(totalUncertain != null)
        {
            statistics.setTotalUncertain(totalUncertain.intValue());
        }
        if(totalRemove != null)
        {
            statistics.setTotalRemove(totalRemove.intValue());
        }
        if(totalCanonicalized != null)
        {
            statistics.setTotalCanonicalized(totalCanonicalized.intValue());
        }
        if(totalFormulasWithCO != null)
        {
            statistics.setTotalFormulasWithCanonicOutput(totalFormulasWithCO.intValue());
        }
        if(totalFormulaMeaningless != null)
        {
            statistics.setTotalFormulaMeaningless(totalFormulaMeaningless.intValue());
        }
        if(totalFormulaRemove != null)
        {
            statistics.setTotalFormulaRemove(totalFormulaRemove.intValue());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Statistics getLatestStatistics()
    {
        Statistics statistics = null;
        try
        {
            statistics = entityManager.createQuery("SELECT s FROM statistics s ORDER BY s.id DESC", Statistics.class).setFirstResult(0).setMaxResults(1).getSingleResult();
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        
        return statistics;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, DateTime> getStatisticsMap()
    {
        List<Object[]> results =
                entityManager.createQuery("SELECT s.id,s.calculationDate FROM statistics s")
                .getResultList();
        
        Map<Long,DateTime> resultMap = new TreeMap<>(Collections.reverseOrder());
        
        for(Object[] result : results)
        {
            resultMap.put((Long) result[0], (DateTime) result[1]);
        }
        
        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Statistics getStatisticsByID(Long id)
    {
        return entityManager.find(Statistics.class, id);
    }    

    public void setCoIsValid(String coIsValid)
    {
        this.coIsValid = coIsValid;
    }

    public void setCoIsInvalid(String coIsInvalid)
    {
        this.coIsInvalid = coIsInvalid;
    }

    public void setCoUncertain(String coUncertain)
    {
        this.coUncertain = coUncertain;
    }

    public void setCoRemove(String coRemove)
    {
        this.coRemove = coRemove;
    }

    public void setFormulaMeaningless(String formulaMeaningless)
    {
        this.formulaMeaningless = formulaMeaningless;
    }

    public void setFormulaRemove(String formulaRemove)
    {
        this.formulaRemove = formulaRemove;
    }

    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
}
