/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.mathmlevaluation.controllers;


import cz.muni.fi.mir.mathmlevaluation.db.domain.Configuration;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Revision;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Statistics;
import cz.muni.fi.mir.mathmlevaluation.db.domain.StatisticsHolder;
import cz.muni.fi.mir.mathmlevaluation.db.service.StatisticsService;
import cz.muni.fi.mir.mathmlevaluation.tools.Pair;
import cz.muni.fi.mir.mathmlevaluation.tools.SiteTitle;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
        ModelMap mm = prepareStatisticsModelMap(statisticsService.getLatestStatistics(),statisticsService.getStatisticsMap());
        
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
        
        return new ModelAndView("redirect:/statistics/");
    }
    
    @RequestMapping(value = "/{id}/",method = RequestMethod.GET)
    @SiteTitle("{statistics.title}")
    public ModelAndView viewStats(@PathVariable Long id)
    {
        ModelMap mm = prepareStatisticsModelMap(statisticsService.getStatisticsByID(id),statisticsService.getStatisticsMap());  
        
        return new ModelAndView("statistics", mm);
    }
    
    private ModelMap prepareStatisticsModelMap(Statistics stat,Map<Long, DateTime> dropdownMap)
    {
        if(stat != null)
        {
            Map<Pair<Configuration,Revision>,SortedMap<String,Integer>> map = new HashMap<>();
            Map<String,Integer> graph = new HashMap<>();
            SortedSet<String> columns = new TreeSet<>();

            for(StatisticsHolder sh : stat.getStatisticsHolders())
            {
                Pair<Configuration,Revision> key = new Pair<>(sh.getConfiguration(), sh.getRevision());

                SortedMap<String,Integer> keyValues = null;

                if(map.containsKey(key))
                {
                    keyValues = map.get(key);

                    addOrIncrement(sh.getAnnotation(), sh.getCount(), keyValues);
                    addOrIncrement(sh.getAnnotation(), sh.getCount(), graph);
                }
                else
                {
                    keyValues = new TreeMap<>();
                    keyValues.put(sh.getAnnotation(), sh.getCount());
                    addOrIncrement(sh.getAnnotation(), sh.getCount(), graph);
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

            ModelMap mm = new ModelMap();

            mm.addAttribute("statisticsMap", map);
            mm.addAttribute("statisticsColumns",columns);
            mm.addAttribute("statisticsDate",stat.getCalculationDate());
            mm.addAttribute("formulaCount", stat.getTotalFormulas());
            mm.addAttribute("coCount", stat.getTotalCanonicOutputs());
            mm.addAttribute("graph", graph);
            mm.addAttribute("statisticsDropdown", dropdownMap);

            return mm;
        }
        else
        {
            return new ModelMap();
        }        
    }

    private void addOrIncrement(String key, Integer value ,Map<String,Integer> map)
    {
        if(map.containsKey(key))
        {
            map.put(key,map.get(key)+value);
        }
        else
        {
            map.put(key,value);
        }
    }
    
}
