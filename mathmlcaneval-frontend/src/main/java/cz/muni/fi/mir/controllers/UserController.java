/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.UserValidator;
import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.UserRoleService;
import cz.muni.fi.mir.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.Tools;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@Controller
@RequestMapping(value ="/user")
public class UserController
{
    @Autowired private UserService userService;
    @Autowired private UserRoleService userRoleService;
    
    
    @RequestMapping("/login/")
    public ModelAndView handleLogin()
    {
        return new ModelAndView("login");
    }
    
    @RequestMapping(value = "/register/", method = RequestMethod.GET)
    public ModelAndView handleRegister()
    {
        return new ModelAndView("register");
    }

    @ModelAttribute("newUser")
    public User getLoginForm() {
        return new User();
    }

    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public ModelAndView createUser(
            @ModelAttribute("newUser") @Valid User user,
            BindingResult result)
    {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, result);

        if (result.hasErrors())
        {
            return new ModelAndView("register");
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

        return new ModelAndView("index");
    }
}
