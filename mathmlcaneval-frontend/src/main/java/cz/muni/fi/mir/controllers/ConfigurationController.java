/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.forms.ConfigurationForm;
import javax.validation.Valid;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@Controller
@RequestMapping(value ="/configuration")
public class ConfigurationController
{
    @Autowired private ConfigurationService configurationService;
    @Autowired private Mapper mapper;
    
    @RequestMapping(value = {"/","/list","/list/"},method = RequestMethod.GET)
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("configurationList", configurationService.getAllCofigurations());
        
        return new ModelAndView("configuration_list",mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.GET)
    public ModelAndView createConfiguration()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("configurationForm", new ConfigurationForm());
        
        return new ModelAndView("configuration_create",mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.POST)
    public ModelAndView createConfigurationSubmit(@Valid @ModelAttribute("configurationForm") ConfigurationForm configurationForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("configurationForm", configurationForm);
            mm.addAttribute(model);
            
            return new ModelAndView("configuration_create",mm);
        }
        else
        {
            configurationService.createConfiguration(mapper.map(configurationForm, Configuration.class));
            
            return new ModelAndView("redirect:/configuration/list/");
        }
    }
    
    @RequestMapping(value={"/view/{id}","/view/{id}/"},method = RequestMethod.GET)
    public ModelAndView viewConfiguration(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("configurationEntry", configurationService.getConfigurationByID(id));
        
        return new ModelAndView("configuration_view",mm);
    }
}
