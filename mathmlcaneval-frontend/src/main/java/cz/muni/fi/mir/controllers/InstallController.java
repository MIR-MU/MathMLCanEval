/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;

/**
 *
 * @author emptka
 */
@RequestMapping("/setup")
@Controller
public class InstallController
{

    private static final Logger logger = Logger.getLogger(InstallController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private SourceDocumentService sourceDocumentService;
    @Autowired
    private Mapper mapper;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView landingPage()
    {
        if (Tools.getInstance().isEmpty(userRoleService.getAllUserRoles()))
        {
            userRoleService.createUserRole(EntityFactory.createUserRole("ROLE_ADMINISTRATOR"));
            userRoleService.createUserRole(EntityFactory.createUserRole("ROLE_USER"));

            ModelMap mm = new ModelMap();
            mm.addAttribute("userForm", new UserForm());

            return new ModelAndView("setup/step1", mm);
        }
        else
        {
            return new ModelAndView("redirect:/reset/");
        }
    }

    @RequestMapping(value = "/step2/", method = RequestMethod.POST)
    public ModelAndView step2(HttpServletRequest request)
    {
        String username = null;
        String email = null;
        String pass1 = null;
        String passVerify = null;
        try
        {
            username = ServletRequestUtils.getStringParameter(request, "username");
            email = ServletRequestUtils.getStringParameter(request, "email");
            pass1 = ServletRequestUtils.getStringParameter(request, "password");
            passVerify = ServletRequestUtils.getStringParameter(request, "passwordVerify");
        }
        catch (ServletRequestBindingException ex)
        {
            logger.error(ex);
        }
        
        if(Tools.getInstance().stringIsEmpty(username))
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("email", email);
            
            return new ModelAndView("setup/step1", mm);
        }
        else if(Tools.getInstance().stringIsEmpty(email))
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("username", username);
            
            return new ModelAndView("setup/step1", mm);
        }
        else if(pass1 != null && !pass1.equals(passVerify))
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("email", email);
            mm.addAttribute("username", username);
            
            return new ModelAndView("setup/step1", mm);
        }
        else
        {
            User u = EntityFactory.createUser(username, pass1, username, email, userRoleService.getAllUserRoles());
            
            u.setPassword(Tools.getInstance().SHA1(u.getPassword()));

            userService.createUser(u);
            
            return new ModelAndView("redirect:/");
        }
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value = "/reset/",method = RequestMethod.GET)
    public ModelAndView handleReset()
    {
//        Configuration cfg = new Configuration();
//        cfg.setProperty(null, null)
        return new ModelAndView("redirect:/");
    }
}
