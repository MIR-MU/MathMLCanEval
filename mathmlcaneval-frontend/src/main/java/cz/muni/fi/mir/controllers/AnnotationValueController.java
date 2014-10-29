/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.service.AnnotationValueSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping(value = "/annotationvalue")
public class AnnotationValueController
{
    @Autowired
    private AnnotationValueSerivce annotationValueSerivce;
    
    @RequestMapping(value={"/","/list","/list/"})
    public ModelAndView list()
    {
        return new ModelAndView("annotationv_list", new ModelMap("annotationValueList",annotationValueSerivce.getAll()));
    }
    
}
