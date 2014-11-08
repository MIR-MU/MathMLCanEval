/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.SiteTitle;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @SiteTitle("{entity.appruns.list}")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("apprunList", applicationRunService.getAllApplicationRunsFromRange(0, 10,false));
        
        
        return new ModelAndView("apprun_list", mm);
    }
    
    
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteApplicationRun(@PathVariable Long id)
    {
        applicationRunService.deleteApplicationRun(applicationRunService.getApplicationRunByID(id),true,true);
        
        
        return new ModelAndView("redirect:/appruns/");
    }
    
    @RequestMapping(value={"/load","/load/"},method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody List<ApplicationRun> loadMore(@RequestParam(value = "start") Integer start,
            @RequestParam(value = "end") Integer end)
    {
        return applicationRunService.getAllApplicationRunsFromRange(start, end,false);
    }
}
