/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.domain.Statistics;
import cz.muni.fi.mir.db.service.StatisticsService;
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

/**
 *
 * @author emptak
 */
public class StatisticsServiceImpl implements StatisticsService
{
    private final String IS_VALID;
    private final String IS_INVALID;
    private final String UNCERTAIN;
    private final String REMOVE_RESULT;

    public StatisticsServiceImpl(String IS_VALID, String IS_INVALID, String UNCERTAIN, String REMOVE_RESULT)
    {
        this.IS_VALID = IS_VALID;
        this.IS_INVALID = IS_INVALID;
        this.UNCERTAIN = UNCERTAIN;
        this.REMOVE_RESULT = REMOVE_RESULT;
    }    

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
    public void executeAtMidnight()
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
            totalValid = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.note LIKE :is_valid",Long.class)
                    .setParameter("is_valid", "%"+IS_VALID+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalInvalid = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.note LIKE :is_invalid",Long.class)
                    .setParameter("is_invalid", "%"+IS_INVALID+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalUncertain = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.note LIKE :is_uncertain",Long.class)
                    .setParameter("is_uncertain", "%"+UNCERTAIN+"%").getSingleResult();
        }
        catch(NoResultException e)
        {
            logger.error(e);
        }
        
        try
        {
            totalRemove = entityManager.createQuery("SELECT COUNT(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.note LIKE :toremove",Long.class)
                    .setParameter("toremove", "%"+REMOVE_RESULT+"%").getSingleResult();
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
            totalFormulaRemove = entityManager.createQuery("SELECT COUNT(f) FROM formula f JOIN f.annotations fa WHERE fa.note LIKE :fRemove1 OR fa.note LIKE :fRemove2", Long.class)
                    .setParameter("fRemove1", "%#formulaRemove%").setParameter("fRemove2", "%#fRemove%").getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.error(nre);
        }
        
        try
        {
            totalFormulaMeaningless = entityManager.createQuery("SELECT COUNT(f) FROM formula f JOIN f.annotations fa WHERE fa.note LIKE :fMeaningles1 OR fa.note LIKE :fMeaningles2", Long.class)
                    .setParameter("fMeaningles1", "%#formulaMeaningless%").setParameter("fMeaningles2", "%#fMeaningless%").getSingleResult();
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
    public Statistics getStatistics()
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
    public List<Statistics> getStatisticsFromRange(DateTime start, DateTime end)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
