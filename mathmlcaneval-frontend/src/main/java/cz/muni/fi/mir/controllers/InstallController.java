/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.services.FileDirectoryService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestParam;

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
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/step2/", method = RequestMethod.POST)
    public ModelAndView step2(@ModelAttribute("userForm") @Valid UserForm user,
            BindingResult result,
            Model model)
    {
        if (result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("userForm", user);
            mm.addAttribute(model);
            
            return new ModelAndView("setup/step1", mm);
        }
        else
        {
            User u = mapper.map(user, User.class);
            u.setUserRoles(userRoleService.getAllUserRoles());
            try
            {
                u.setPassword(Tools.getInstance().SHA1(u.getPassword()));

            }
            catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
            {
                logger.info(ex);
            }
            userService.createUser(u);
            
            return new ModelAndView("setup/step2");
        }
    }
    
    @RequestMapping(value="/step3/",method = RequestMethod.POST)
    public ModelAndView step3(@RequestParam("path") String path,@RequestParam("filename") String filename)
    {
        if(!Tools.getInstance().stringIsEmpty(path))
        {
            FileDirectoryService fds = new FileDirectoryService();
            List<SourceDocument> list = null;
            try
            {
                list = fds.exploreDirectory(path, Tools.getInstance().stringIsEmpty(filename) ? "*.xml" : filename);
            }
            catch(FileNotFoundException ex)
            {
                logger.fatal(ex);
            }
            if(list != null)
            {
                for(SourceDocument sd : list)
                {
                    logger.info("Init import of : "+sd);
                    sourceDocumentService.createSourceDocument(sd);
                }
            }
            
            return new ModelAndView("setup/step3");
        }
        else
        {
            return null;
        }
    }

}
