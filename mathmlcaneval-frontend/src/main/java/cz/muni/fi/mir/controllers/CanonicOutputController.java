/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.service.AnnotationService;
import cz.muni.fi.mir.db.service.AnnotationValueSerivce;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.forms.AnnotationForm;
import cz.muni.fi.mir.forms.FindSimilarForm;
import cz.muni.fi.mir.tools.AnnotationAction;
import cz.muni.fi.mir.tools.SiteTitle;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import org.dozer.Mapper;
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
    private Mapper mapper;
    @Autowired
    private SecurityContextFacade securityContext;

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
}
