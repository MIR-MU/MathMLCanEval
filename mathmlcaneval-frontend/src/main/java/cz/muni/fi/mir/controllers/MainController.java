/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@Controller
public class MainController
{
    @Autowired private StatisticsService statisticsService;
    
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView handleIndex()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("statistics", statisticsService.getStatistics());
        return new ModelAndView("index",mm);
    }
    
    
    @RequestMapping(value="/errors/404.html")
    public ModelAndView handle404()
    {
        return new ModelAndView("errors/404");
    }
}
