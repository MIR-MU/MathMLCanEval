/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.audit.AuditorService;
import cz.muni.fi.mir.db.service.StatisticsService;
import cz.muni.fi.mir.tools.SiteTitle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
@SiteTitle(mainTitle = "{website.title}")
public class MainController
{
    private static final Logger logger = Logger.getLogger(MainController.class);
    @Autowired private StatisticsService statisticsService;
    @Autowired private AuditorService auditorService;
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView handleIndex()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("statistics", statisticsService.getLatestStatistics());
        mm.addAttribute("minifeed", auditorService.getLatestEvents());
        return new ModelAndView("index",mm);
    }
    
    
    
    
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/logger/", "/logger"})
    public ModelAndView logger()
    {
        ModelMap mm = new ModelMap();

        return new ModelAndView("logger",mm);
    }
    
    @RequestMapping(value="/errors/404.html")
    public ModelAndView handle404()
    {
        return new ModelAndView("errors/404");
    }
}
