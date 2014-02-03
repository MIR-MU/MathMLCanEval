/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@Controller
public class MainController
{
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView handleIndex()
    {
        return new ModelAndView("index");
    }
    
    
    @RequestMapping(value="/errors/404.html")
    public ModelAndView handle404()
    {
        return new ModelAndView("errors/404");
    }
}
