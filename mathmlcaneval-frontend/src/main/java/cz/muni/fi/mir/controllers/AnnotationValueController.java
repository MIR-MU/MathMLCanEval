/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.AnnotationValue;
import cz.muni.fi.mir.db.service.AnnotationValueSerivce;
import cz.muni.fi.mir.forms.AnnotationValueForm;
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
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping(value = "/annotationvalue")
public class AnnotationValueController
{
    @Autowired
    private AnnotationValueSerivce annotationValueSerivce;
    
    @Autowired
    private Mapper mapper;
    
    @RequestMapping(value={"/","/list","/list/"})
    public ModelAndView list()
    {
        return new ModelAndView("annotationv_list", new ModelMap("annotationValueList",annotationValueSerivce.getAll()));
    }
    
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"})
    public ModelAndView delete(@PathVariable Long id)
    {
        AnnotationValue av = new AnnotationValue();
        av.setId(id);
        
        annotationValueSerivce.deleteAnnotationValue(av);
        
        return new ModelAndView("annotationv_list", new ModelMap("annotationValueList",annotationValueSerivce.getAll()));
    }
    
    @RequestMapping(value = {"/edit/{id}","/edit/{id}/"},method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id)
    {
        AnnotationValue av = annotationValueSerivce.getAnnotationValueByID(id);
        ModelMap mm = new ModelMap("annotationValueForm", mapper.map(av, AnnotationValueForm.class));
        
        return new ModelAndView("annotationv_edit",mm);
    }
    
    @RequestMapping(value={"/edit","/edit/"},method = RequestMethod.POST)
    public ModelAndView editSubmit(@Valid 
    @ModelAttribute("annotationValueForm") 
            AnnotationValueForm annotationValueForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("annotationValueForm", annotationValueForm);
            mm.addAttribute(model);
        }
        else
        {
            annotationValueSerivce.updateAnnotationValue(mapper.map(annotationValueForm, AnnotationValue.class));
        }
        return new ModelAndView("redirect:/annotationvalue/");
    }
    
}
