/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;


import cz.muni.fi.mir.db.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
        ModelMap mm = new ModelMap();
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
        
        return new ModelAndView("redirect:/statistics/");
    }

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/logger/", "/logger"})
    public ModelAndView logger()
    {
        ModelMap mm = new ModelMap();

        return new ModelAndView("logger",mm);
    }
}
