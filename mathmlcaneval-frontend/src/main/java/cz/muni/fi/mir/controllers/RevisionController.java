/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.forms.RevisionForm;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.SiteTitle;

/**
 *
 * @author Empt
 */
@Controller
@RequestMapping(value ="/revision")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class RevisionController
{
    @Autowired private RevisionService revisionService;
    @Autowired private Mapper mapper;
    
    @RequestMapping(value = {"/","/list/","/list"},method = RequestMethod.GET)
    @SiteTitle("{navigation.revision.list}")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("revisionList",revisionService.getAllRevisions());        
        
        return new ModelAndView("revision_list",mm);
    }
    
    
    @RequestMapping(value = {"/create/","/create"},method = RequestMethod.GET)
    @SiteTitle("{navigation.revision.create}")
    public ModelAndView createRevision()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("revisionForm", new RevisionForm());
        
        return new ModelAndView("revision_create", mm);
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/create/","/create"},method = RequestMethod.POST)
    @SiteTitle("{navigation.revision.create}")
    public ModelAndView createRevisionSubmit(@Valid @ModelAttribute("revisionForm") RevisionForm revisionForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("revisionForm", revisionForm);
            mm.addAttribute(model);
            
            return new ModelAndView("revision_create",mm);
        }
        else
        {
            revisionService.createRevision(mapper.map(revisionForm, Revision.class));
            
            return new ModelAndView("redirect:/revision/list/");
        }
    }
    
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteRevision(@PathVariable Long id)
    {
        revisionService.deleteRevision(EntityFactory.createRevision(id));
        
        return new ModelAndView("redirect:/revision/list/");
    }
    
    
    @RequestMapping(value ={"/edit/{id}","/edit/{id}/"},method = RequestMethod.GET)
    @SiteTitle("{entity.revision.edit}")
    public ModelAndView editRevision(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("revisionForm", mapper.map(revisionService.getRevisionByID(id),RevisionForm.class));
        
        return new ModelAndView("revision_edit",mm);
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value={"/edit/","/edit/"}, method = RequestMethod.POST)
    @SiteTitle("{entity.revision.edit}")
    public ModelAndView editRevisionSubmit(@Valid @ModelAttribute("revisionForm") RevisionForm revisionForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("revisionForm", revisionForm);
            mm.addAttribute(model);
            
            return new ModelAndView("revision_edit",mm);
        }
        else
        {
            revisionService.updateRevision(mapper.map(revisionForm, Revision.class));
            
            return new ModelAndView("redirect:/revision/list/");
        }
    }
}
