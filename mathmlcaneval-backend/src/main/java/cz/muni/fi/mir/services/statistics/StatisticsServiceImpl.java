/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.services.statistics;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emptak
 */
@Repository(value = "statisticsService")
public class StatisticsServiceImpl implements StatisticsService
{
    private String IS_VALID;
    
    
    public StatisticsServiceImpl(String is_valid)
    {
        this.IS_VALID = is_valid;
    }

    @PersistenceContext
    private EntityManager entityManager;
    
    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class);
    
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public void calculate()
    {
        Statistics statistics = new Statistics();
        
        calc(statistics);
        entityManager.persist(statistics);
        
    }
    
    
    private void calc(Statistics statistics)
    {
        statistics.setCalculationDate(DateTime.now());
        Long totalFormulas = null;
        Long totalCanonicalized = null;
        Long totalValid = null;
        Long totalInvalid;
        Long totalUncertain;
        Long totalRemove;
        
        try
        {
            totalFormulas = entityManager.createQuery("SELECT count(f) FROM formula f", Long.class).getSingleResult();
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        
        try
        {
            totalValid = entityManager.createQuery("SELECT count(co) FROM canonicOutput co JOIN co.annotations coa WHERE coa.note LIKE :is_valid",Long.class)
                    .setParameter("is_valid", IS_VALID).getSingleResult();
        }
        catch(Exception e)
        {
            logger.error(e);
        }
        
        
        
        
        if(totalFormulas != null)
        {
            statistics.setTotalFormulas(totalFormulas.intValue());
        }
        if(totalValid != null)
        {
            statistics.setTotalValid(totalValid.intValue());
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
}
