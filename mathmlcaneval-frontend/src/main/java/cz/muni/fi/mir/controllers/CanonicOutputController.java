/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import java.util.List;
import java.util.Map;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.SearchResponse;
import cz.muni.fi.mir.db.service.AnnotationValueSerivce;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.forms.AnnotationForm;
import cz.muni.fi.mir.forms.FindSimilarForm;
import cz.muni.fi.mir.tools.AnnotationAction;
import cz.muni.fi.mir.tools.SiteTitle;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;

import org.apache.commons.collections.ListUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author siska
 */
@Controller
@RequestMapping(value = "/canonicoutput")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class CanonicOutputController
{
    @Autowired
    private CanonicOutputService canonicOutputService;
    @Autowired
    private AnnotationValueSerivce annotationValueSerivce;
    @Autowired
    private ApplicationRunService applicationRunService;


    @RequestMapping(value = {"/view/{id}", "/view/{id}/"}, method = RequestMethod.GET)
    @SiteTitle("{entity.canonicOutput.view}")
    public ModelAndView viewCanonicOutput(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        CanonicOutput canonicOutput = canonicOutputService.getCanonicOutputByID(id);

        mm.addAttribute("canonicOutput", canonicOutput);
        mm.addAttribute("annotationForm", new AnnotationForm());
        mm.addAttribute("findSimilarForm", new FindSimilarForm());
        mm.addAttribute("annotationAction", new AnnotationAction());
        mm.addAttribute("nextCanonicOutput", canonicOutputService.nextInRun(canonicOutput));
        mm.addAttribute("firstCanonicOutput", canonicOutputService.firstInRun(canonicOutput));
        mm.addAttribute("lastCanonicOutput", canonicOutputService.lastInRun(canonicOutput));
        mm.addAttribute("previousCanonicOutput", canonicOutputService.previousInRun(canonicOutput));
        mm.addAttribute("annotationValueList", annotationValueSerivce.getAllForCanonicOutputs());
        
        

        return new ModelAndView("canonicoutput_view", mm);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteCanonicOutput(@PathVariable Long id)
    {
        canonicOutputService.deleteCanonicOutput(canonicOutputService.getCanonicOutputByID(id));
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = {"/list/{filters}","/list/{filters}/"},method = RequestMethod.GET)
    public ModelAndView filterList(@MatrixVariable(pathVar = "filters") Map<String,List<String>> filters, @ModelAttribute("pagination") Pagination pagination)
    {
        ModelMap mm = new ModelMap();
        if(filters.containsKey("apprun"))
        {
            ApplicationRun applicationRun = applicationRunService.getApplicationRunByID(Long.valueOf(filters.get("apprun").get(0)));
            SearchResponse<CanonicOutput> result = canonicOutputService.getCanonicOutputByAppRun(applicationRun, pagination);
            pagination.setNumberOfRecords(result.getTotalResultSize());
            mm.addAttribute("pagination", pagination);
            mm.addAttribute("outputList", result.getResults());
        }
        else
        {
            mm.addAttribute("outputList", ListUtils.EMPTY_LIST);
        }

        return new ModelAndView("canonicoutput_list",mm);
    }
}
