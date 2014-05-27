/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.services.statistics;

import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author emptak
 */
public interface StatisticsService
{
    
    void calculate();
    
    Statistics getStatistics();
    
    List<Statistics> getStatisticsFromRange(DateTime start, DateTime end);
    
}
