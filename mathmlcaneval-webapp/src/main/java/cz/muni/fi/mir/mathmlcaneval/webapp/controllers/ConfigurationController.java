/*
 * Copyright 2016 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.webapp.controllers;

import cz.muni.fi.mir.mathmlcaneval.api.ConfigurationService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.ConfigurationDTO;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import cz.muni.fi.mir.mathmlcaneval.webapp.forms.ConfigurationForm;
import javax.validation.Valid;
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
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping("/configuration")
public class ConfigurationController
{
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private Mapper mapper;
    
    @RequestMapping("/")
    public ModelAndView listEnabled()
    {
        ModelMap mm = new ModelMap("configurationList", configurationService.getAllEnabled());
        
        return new ModelAndView("configuration.list", mm);
    }
    
    @RequestMapping("/all/")
    public ModelAndView listAll()
    {
        ModelMap mm = new ModelMap("configurationList", configurationService.getAll());
        
        return new ModelAndView("configuration.list", mm);
    }
    
    @RequestMapping(value="/submit/",method = RequestMethod.GET)
    public ModelAndView submit()
    {
        ModelMap mm = new ModelMap("configurationForm", new ConfigurationForm());
        
        return new ModelAndView("configuration.submit", mm);
    }
    @RequestMapping(value="/submit/",method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("configurationForm")@Valid ConfigurationForm configurationForm,BindingResult result, Model model)
    {
        ConfigurationDTO config = mapper.map(configurationForm, ConfigurationDTO.class);
        
        configurationService.createConfiguration(config);
        
        return new ModelAndView("redirect:/configuration/");
    }
    
    @RequestMapping(value="/disable/{id}/",method = RequestMethod.GET)
    public ModelAndView disableConfiguration(@PathVariable Long id)
    {
        ConfigurationDTO config = configurationService.getConfigurationByID(id);
        configurationService.disableConfiguration(config);
        
        return new ModelAndView("redirect:/configuration/");
    }
    
    /*    
    void enableConfiguration(ConfigurationDTO configuration) throws IllegalArgumentException;
    void changeNote(ConfigurationDTO configuration) throws IllegalArgumentException;
    ConfigurationDTO getConfigurationByID(Long id) throws IllegalArgumentException;
    */
}
