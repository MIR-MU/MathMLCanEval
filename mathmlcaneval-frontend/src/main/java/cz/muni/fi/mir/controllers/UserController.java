/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.db.service.UserRoleService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Robert Siska
 */
@Controller
@RequestMapping(value ="/user")
public class UserController
{
    @Autowired private UserService userService;
    @Autowired private UserRoleService userRoleService;
    @Autowired private Mapper mapper;
    @Autowired private SecurityContextFacade securityContext;
    
    
    @RequestMapping(value = {"/","/list","/list/"},method = RequestMethod.GET)
    public ModelAndView list()
    {        
        ModelMap mm = new ModelMap();
        mm.addAttribute("userList", userService.getAllUsers());
        
        return new ModelAndView("user_list",mm);
    }
    
    @RequestMapping("/login/")
    public ModelAndView handleLogin()
    {
        return new ModelAndView("login");
    }
    
    @RequestMapping(value = {"/create","/create/"}, method = RequestMethod.GET)
    public ModelAndView handleRegister()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userForm", new UserForm());
        mm.addAttribute("userRolesFormList", userRoleService.getAllUserRoles());
        return new ModelAndView("user_create",mm);
    }

    @RequestMapping(value = {"/create","/create/"}, method = RequestMethod.POST)
    public ModelAndView createUser(
            @ModelAttribute("userForm") @Valid UserForm user,
            BindingResult result)
    {
        if (result.hasErrors())
        {
            return new ModelAndView("user_create");
        }
        

        UserRole userRole = userRoleService.getUserRoleByName("ROLE_USER");
        User u = null;
        try {
            String encodedPassword = Tools.getInstance().SHA1(user.getPassword());
            u = EntityFactory.createUser(user.getUsername(), encodedPassword, user.getRealName(), userRole);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userService.createUser(u);

        return new ModelAndView("redirect:/user/list/");
    }
    
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(EntityFactory.createUser(id));
        
        return new ModelAndView("redirect:/user/list/");
    }
    
    @RequestMapping(value= {"/profile","/profile/"},method = RequestMethod.GET)
    public ModelAndView showProfile()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userForm", mapper.map(userService.getUserByUsername(securityContext.getLoggedUser()), UserForm.class));
        
        return new ModelAndView("user_edit",mm);
    }
    
    @RequestMapping(value={"/edit","/edit/"},method = RequestMethod.GET)
    public ModelAndView editUser()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("userForm", new UserForm());
        
        return new ModelAndView("user_edit",mm);        
    }
    
    /**
     * handles also update of user profile
     * @param userForm
     * @param result
     * @param model
     * @return 
     */
    @RequestMapping(value={"/edit","/edit/"},method = RequestMethod.POST)
    public ModelAndView editUserSubmit(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("userForm", userForm);
            mm.addAttribute(model);
            
            return new ModelAndView("user_edit",mm);
        }
        else
        {
            userService.updateUser(mapper.map(userForm,User.class));
            
            return new ModelAndView("redirect:/");
        }
    }
}
