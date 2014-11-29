/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlevaluation.controllers;

import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlevaluation.db.service.ComparisonService;
import cz.muni.fi.mir.mathmlevaluation.tools.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping(value = "/comparison")
public class ComparisonController
{
    @Autowired private ComparisonService comparisonService;
    @Autowired private ApplicationRunService applicationRunService;
    
    @RequestMapping(value = "/")
    public ModelAndView listDifference(@RequestParam(value = "appRunsID") Long[] appRunsID)
    {
        if(appRunsID == null || appRunsID.length != 2)
        {
            throw new IllegalArgumentException("Wrong comparison request size.");
        }
        
        ModelMap mm = new ModelMap("compareDiff",true);
        mm.addAttribute("comparedResult",
                comparisonService.compare(idToARun(appRunsID[0]),
                        idToARun(appRunsID[1])
                ));
        mm.addAttribute("applicationRun1", applicationRunService.getApplicationRunByID(appRunsID[0]));
        mm.addAttribute("applicationRun2", applicationRunService.getApplicationRunByID(appRunsID[1]));
        return new ModelAndView("comparisonResult", mm);
    }
    
    
    private ApplicationRun idToARun(Long id)
    {
        ApplicationRun ar = new ApplicationRun();
        ar.setId(id);
        
        return ar;
    }
}
