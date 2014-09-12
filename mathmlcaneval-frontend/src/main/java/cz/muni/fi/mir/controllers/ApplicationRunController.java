/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.tools.SiteTitle;
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
@RequestMapping("/appruns")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class ApplicationRunController
{
    
    @Autowired 
    private ApplicationRunService applicationRunService;
    
    @RequestMapping(value = {"/","/list","/list/"},method = RequestMethod.GET)
    @SiteTitle("{entity.applicationrun.list}")
    public ModelAndView list()
    {
        ModelMap mm = prepareModelMap();
        mm.addAttribute("apprunList", applicationRunService.getAllApplicationRuns());
        
        
        return new ModelAndView("apprun_list",mm);
    }
    
    
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteApplicationRun(@PathVariable Long id)
    {
        applicationRunService.deleteApplicationRun(applicationRunService.getApplicationRunByID(id),true,true);
        
        
        return new ModelAndView("redirect:/appruns/");
    }
    
    
    
    
    private ModelMap prepareModelMap()
    {
        ModelMap mm = new ModelMap();
        
        return mm;
    }
}
