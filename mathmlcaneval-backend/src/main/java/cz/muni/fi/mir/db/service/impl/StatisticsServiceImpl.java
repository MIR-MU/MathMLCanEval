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

import cz.muni.fi.mir.db.dao.AnnotationValueDAO;
import cz.muni.fi.mir.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.db.dao.FormulaDAO;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationValue;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.mir.db.domain.Statistics;
import cz.muni.fi.mir.db.domain.StatisticsHolder;
import cz.muni.fi.mir.db.service.StatisticsService;
import java.util.ArrayList;
import javax.persistence.NoResultException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service(value = "statisticsService")
public class StatisticsServiceImpl implements StatisticsService
{
    @Autowired private AnnotationValueDAO annotationValueDAO;
    @Autowired private CanonicOutputDAO canonicOutputDAO;
    @Autowired private FormulaDAO formulaDAO;   
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class);
    
    @Override
    @Transactional(readOnly = false)
    public void calculate()
    {
        Statistics statistics = new Statistics();
        
        List<AnnotationValue> aValues = annotationValueDAO.getAll();
        List<StatisticsHolder> holders = new ArrayList<>();
        
        for(AnnotationValue av : aValues)
        {
            List<Annotation> annotations = entityManager.createQuery("SELECT a FROM annotation a WHERE a.annotationContent LIKE :annotationTag", Annotation.class)
                    .setParameter("annotationTag", av.getValue()).getResultList();
            if(annotations.isEmpty())
            {
                StatisticsHolder sh = new StatisticsHolder();
                sh.setAnnotation(av.getValue());
                sh.setCount(annotations.size());

                holders.add(sh);
            }
            else
            {
                for(Annotation a : annotations)
                { 
                    StatisticsHolder sHolder = new StatisticsHolder();
                    boolean found = false;
                    if(av.getType().equals(AnnotationValue.Type.CANONICOUTPUT))
                    {
                        CanonicOutput co = canonicOutputDAO.getCanonicOutputByAnnotation(a);
                        Hibernate.initialize(co.getApplicationRun());                        
                        for(StatisticsHolder sh : holders)
                        {
                            if(sh.getConfiguration() != null && 
                                    sh.getConfiguration().equals(co.getApplicationRun().getConfiguration()) &&
                                    sh.getRevision() != null && sh.getRevision().equals(co.getApplicationRun().getRevision())
                            )
                            {
                                // at this point this annotation belongs has proper 
                                // value and belongs to proper group
                                sh.setCount(sh.getCount()+1);
                                found = true;
                                break;                                
                            }
                        }
                        if(!found)
                        {
                            sHolder.setConfiguration(co.getApplicationRun().getConfiguration());
                            sHolder.setRevision(co.getApplicationRun().getRevision());
                        }                        
                    } 
                    if(!found)
                    {
                        sHolder.setAnnotation(av.getValue());
                        sHolder.setCount(1);
                        holders.add(sHolder);
                    }                    
                }
            }
        }
        
        statistics.setStatisticsHolders(holders);
        statistics.setCalculationDate(DateTime.now());
        statistics.setTotalFormulas(formulaDAO.getNumberOfRecords());
        
        int totalCanon = 0;
        
        try
        {
            totalCanon = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co", Long.class)
                    .getSingleResult().intValue();
        }
        catch(NoResultException nre)
        {
            logger.info(nre);
        }
        
        statistics.setTotalCanonicOutputs(totalCanon);
        
        entityManager.persist(statistics);        
    }
    
    @Scheduled(cron = "${statistics.generate.cron}" )
    @Transactional(readOnly = false)
    public void scheduledCalculation()
    {
        calculate();
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
}
