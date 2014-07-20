/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;


import cz.muni.fi.mir.db.domain.Statistics;
import cz.muni.fi.mir.db.service.StatisticsService;
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
public class StatisticsController
{
    @Autowired StatisticsService statisticsService;
    
    
    @RequestMapping(value = "/")
    public ModelAndView list()
    {
        ModelMap mm = prepareModelMap();
        mm.addAttribute("statistics", statisticsService.getStatistics());
        
        return new ModelAndView("statistics",mm);
    }
    
    @RequestMapping(value = "/calc/")
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
        mm.addAttribute("statistics", statisticsService.getStatistics());
        
        return new ModelAndView("statistics",mm);
    }
    
    @RequestMapping(value = "/{id}/",method = RequestMethod.GET)
    public ModelAndView viewStats(@PathVariable Long id)
    {
        Statistics stat = statisticsService.getStatisticsByID(id);
        ModelMap mm = prepareModelMap();
        mm.addAttribute("statistics", stat);
        
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
