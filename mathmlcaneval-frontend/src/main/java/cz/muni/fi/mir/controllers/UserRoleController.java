/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.forms.UserRoleForm;
import cz.muni.fi.mir.tools.EntityFactory;
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
 * @author Dominik Szalai
 */
@Controller
@RequestMapping(value ="/userrole")
public class UserRoleController
{
    @Autowired private UserRoleService userRoleService;
    @Autowired private Mapper mapper;
    
    @RequestMapping(value={"/","/list","/list/"},method = RequestMethod.GET)
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userRoleList", userRoleService.getAllUserRoles());
        
        return new ModelAndView("userrole_list", mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.GET)
    public ModelAndView createUserRole()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userRoleForm", new UserRoleForm());
        
        return new ModelAndView("userrole_create", mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.POST)
    public ModelAndView createUserRoleSubimt(@Valid @ModelAttribute("userRoleForm") UserRoleForm userRoleForm,BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute(model);
            mm.addAttribute("userRoleForm", userRoleForm);
            
            return new ModelAndView("user_create",mm);
        }
        else
        {
            userRoleService.createUserRole(mapper.map(userRoleForm, UserRole.class));
            
            return new ModelAndView("redirect:/userrole/list/");
        }
    }
    
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteUserRole(@PathVariable Long id)
    {
        userRoleService.deleteUserRole(EntityFactory.createUserRole(id));
        
        return new ModelAndView("redirect:/userrole/list/");
    }
    
    @RequestMapping(value={"/edit/{id}","/edit/{id}/"},method = RequestMethod.GET)
    public ModelAndView editUserRole(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userRoleForm", mapper.map(userRoleService.getUserRoleByID(id),UserRoleForm.class));
        
        return new ModelAndView("userrole_edit", mm);
    }
    
    @RequestMapping(value={"/edit","/edit/"},method = RequestMethod.POST)
    public ModelAndView editUserRoleSubimt(@Valid @ModelAttribute("userRoleForm") UserRoleForm userRoleForm,BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute(model);
            mm.addAttribute("userRoleForm", userRoleForm);
            
            return new ModelAndView("user_edit",mm);
        }
        else
        {
            userRoleService.updateUserRole(mapper.map(userRoleForm, UserRole.class));
            
            return new ModelAndView("redirect:/userrole/list/");
        }
    }
    
}
