/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@Controller
@RequestMapping(value ="/user")
public class UserController
{
    
    
    @RequestMapping("/login/")
    public ModelAndView handleLogin()
    {
        return new ModelAndView("login");
    }
    
}
