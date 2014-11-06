/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;


import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.Statistics;
import cz.muni.fi.mir.db.domain.StatisticsHolder;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.StatisticsService;
import cz.muni.fi.mir.tools.Pair;
import cz.muni.fi.mir.tools.SiteTitle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@Controller
@RequestMapping(value = "/statistics")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class StatisticsController
{
    @Autowired private StatisticsService statisticsService;
    
    @RequestMapping(value = "/")
    @SiteTitle("{statistics.title}")
    public ModelAndView list()
    {
        ModelMap mm = prepareModelMap();
        mm.addAttribute("statistics", statisticsService.getLatestStatistics());
        
        return new ModelAndView("statistics",mm);
    }
    
    @RequestMapping(value = "/calc/")
    @SiteTitle("{statistics.title}")
    public ModelAndView calc()
    {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                statisticsService.calculate();
            }
        };
        
        new Thread(r).start();
        ModelMap mm = prepareModelMap();
        mm.addAttribute("statisticsMessage", "statistics.calc.started");
        mm.addAttribute("statistics", statisticsService.getLatestStatistics());
        
        return new ModelAndView("statistics",mm);
    }
    
    @RequestMapping(value = "/{id}/",method = RequestMethod.GET)
    @SiteTitle("{statistics.title}")
    public ModelAndView viewStats(@PathVariable Long id)
    {
        Map<Pair<Configuration,Revision>,SortedMap<String,Integer>> map = new HashMap<>();
        SortedSet<String> columns = new TreeSet<>();
        
//        for(Configuration c : configs)
//        {
//            for(Revision r : revisions)
//            {
//                map.put(new Pair<>(c, r), null);
//            }
//        }
        
        
        
        Statistics stat = statisticsService.getStatisticsByID(id);
        
        for(StatisticsHolder sh : stat.getStatisticsHolders())
        {
            Pair<Configuration,Revision> key = new Pair<>(sh.getConfiguration(), sh.getRevision());
            
            SortedMap<String,Integer> keyValues = null;
            
            if(map.containsKey(key))
            {
                keyValues = map.get(key);
                if(keyValues.containsKey(sh.getAnnotation()))
                {
                    keyValues.put(sh.getAnnotation(), keyValues.get(sh.getAnnotation())+sh.getCount());
                }
                else
                {
                    keyValues.put(sh.getAnnotation(), sh.getCount());
                }
            }
            else
            {
                keyValues = new TreeMap<>();
                keyValues.put(sh.getAnnotation(), sh.getCount());
            }
            
            
            map.put(key,keyValues);
            columns.add(sh.getAnnotation());
        }
        
        //postprocessing to fill empty columns
        for(Pair<Configuration,Revision> pair : map.keySet())
        {
            SortedMap<String,Integer> values = map.get(pair);
            for(String s : columns)
            {
                if(!values.containsKey(s))
                {
                    values.put(s, 0);
                }
            }
            
            map.put(pair, values);
        }
        
        ModelMap mm = prepareModelMap();
        mm.addAttribute("statisticsMap", map);
        mm.addAttribute("statisticsColumns",columns);
        
        return new ModelAndView("statistics", mm);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/logger/", "/logger"})
    public ModelAndView logger()
    {
        ModelMap mm = new ModelMap();

        return new ModelAndView("logger",mm);
    }
    
    private ModelMap prepareModelMap()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("statisticsList", statisticsService.getStatisticsMap());
        
        return mm;
    }
}
