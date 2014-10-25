/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.db.service ;
import cz.muni.fi.mir.db.domain.Statistics;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface StatisticsService
{
    /**
     * 
     */
    void calculate();
    
    /**
     * 
     * @param id
     * @return
     * @throws IllegalArgumentException 
     */
    Statistics getStatisticsByID(Long id) throws IllegalArgumentException;
    
    /**
     * 
     * @return 
     */
    Statistics getStatistics();
    
    /**
     * 
     * @return 
     */
    Map<Long,DateTime> getStatisticsMap();
    
}
