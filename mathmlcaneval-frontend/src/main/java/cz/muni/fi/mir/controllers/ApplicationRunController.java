/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

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

import org.dozer.Mapper;
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
    private FormulaService formulaService;
    @Autowired 
    private ApplicationRunService applicationRunService;
    @Autowired
    private RevisionService revisionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private SourceDocumentService sourceDocumentService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private SecurityContextFacade securityContext;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;

    
    @RequestMapping(value = {"/","/list","/list/"},method = RequestMethod.GET)
    @SiteTitle("{entity.appruns.list}")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("apprunList", applicationRunService.getAllApplicationRuns());
        
        
        return new ModelAndView("apprun_list", mm);
    }
    
    
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteApplicationRun(@PathVariable Long id)
    {
        applicationRunService.deleteApplicationRun(applicationRunService.getApplicationRunByID(id),true,true);
        
        
        return new ModelAndView("redirect:/appruns/");
    }
}
