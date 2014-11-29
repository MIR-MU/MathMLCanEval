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

import cz.muni.fi.mir.mathmlevaluation.db.dao.AnnotationValueDAO;
import cz.muni.fi.mir.mathmlevaluation.db.dao.CanonicOutputDAO;
import cz.muni.fi.mir.mathmlevaluation.db.dao.FormulaDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Annotation;
import cz.muni.fi.mir.mathmlevaluation.db.domain.AnnotationValue;
import cz.muni.fi.mir.mathmlevaluation.db.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Configuration;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Revision;
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

import cz.muni.fi.mir.mathmlevaluation.db.domain.Statistics;
import cz.muni.fi.mir.mathmlevaluation.db.domain.StatisticsHolder;
import cz.muni.fi.mir.mathmlevaluation.db.service.StatisticsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
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
        Map<StatPair,Map<String,Integer>> temp = new HashMap<>();
        
        List<AnnotationValue> aValues = annotationValueDAO.getAll();
        List<StatisticsHolder> holders = new ArrayList<>();
        
        for(AnnotationValue av : aValues)
        {
            List<Annotation> annotations = entityManager.createQuery("SELECT a FROM annotation a WHERE a.annotationContent LIKE :annotationTag", Annotation.class)
                    .setParameter("annotationTag", "%"+av.getValue()+"%").getResultList();
            logger.debug(av.getValue()+"$"+annotations.size());
            if(annotations.isEmpty())
            {
                continue;
            }
            else
            {
                for(Annotation a : annotations)
                {
                    logger.trace("Procesing annotation:"+a.getAnnotationContent());
                    if(av.getType().equals(AnnotationValue.Type.CANONICOUTPUT))
                    {
                        logger.trace("Matched type is canonic output");
                        CanonicOutput co = canonicOutputDAO.getCanonicOutputByAnnotation(a);
                        Hibernate.initialize(co.getApplicationRun());
                        StatPair sp = new StatPair(co.getApplicationRun().getConfiguration(), co.getApplicationRun().getRevision());
                        
                        logger.trace("Pair: "+sp);
                        
                        if(temp.containsKey(sp))
                        {
                            logger.trace("Pair is there");
                            Map<String,Integer> subResult = temp.get(sp);
                            if(subResult.containsKey(av.getValue()))
                            {
                                logger.trace("SubResult contains key "+av.getValue());
                                logger.trace("Old subresult "+subResult.get(av.getValue()));
                                int i = subResult.get(av.getValue())+1;
                                logger.trace("Putting  "+av.getValue()+" with value "+i);
                                subResult.put(av.getValue(), i);
                            }
                            else
                            {
                                logger.trace("SubResult does not contain key "+av.getValue());
                                logger.trace("Putting "+av.getValue()+" with value "+1);
                                subResult.put(av.getValue(),1);
                            }
                            
                            temp.put(sp, subResult);
                        }
                        else
                        {
                            logger.trace("Pair is missing");
                            Map<String,Integer> subResult = new HashMap();
                            subResult.put(av.getValue(),1);
                            logger.trace("Putting pair "+ sp +" value "+subResult);
                            temp.put(sp, subResult);
                        }
                    }
                    
                }                
            }
        }
        
        for(StatPair sp : temp.keySet())
        {
            Map<String,Integer> subResults = temp.get(sp);
            for(String s : subResults.keySet())
            {
                StatisticsHolder sh = new StatisticsHolder();
                sh.setAnnotation(s);
                sh.setConfiguration(sp.getConfiguration());
                sh.setRevision(sp.getRevision());
                sh.setCount(subResults.get(s));

                holders.add(sh);
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
    
    private class StatPair
    {
        private Configuration configuration;
        private Revision revision;

        public StatPair()
        {
        }

        public StatPair(Configuration configuration, Revision revision)
        {
            this.configuration = configuration;
            this.revision = revision;
        }

        public Configuration getConfiguration()
        {
            return configuration;
        }

        public void setConfiguration(Configuration configuration)
        {
            this.configuration = configuration;
        }

        public Revision getRevision()
        {
            return revision;
        }

        public void setRevision(Revision revision)
        {
            this.revision = revision;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 83 * hash + Objects.hashCode(this.configuration);
            hash = 83 * hash + Objects.hashCode(this.revision);
            return hash;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final StatPair other = (StatPair) obj;
            if (!Objects.equals(this.configuration, other.configuration))
            {
                return false;
            }
            return Objects.equals(this.revision, other.revision);
        }

        @Override
        public String toString()
        {
            return "StatPair{" + "c=" + configuration + ", r=" + revision + '}';
        }
        
        
    }
}
