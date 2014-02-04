/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.forms.SourceDocumentForm;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Empt
 */
@Controller
@RequestMapping(value = "/sourcedocument")
public class SourceDocumentController
{
    @Autowired private SourceDocumentService sourceDocumentService;
    
    @RequestMapping(value={"/","/list","/list/"},method = RequestMethod.GET)
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("sourceDocumentList", sourceDocumentService.getAllDocuments());
        
        return new ModelAndView("sourcedocument_list",mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.GET)
    public ModelAndView create()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("sourceDocumentForm", new SourceDocumentForm());
        
        return new ModelAndView("sourcedocument_create",mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.POST)
    public ModelAndView createSubmit(@Valid @ModelAttribute("sourceDocumentForm") SourceDocumentForm sourceDocumentForm,
            BindingResult result, Model model,HttpServletRequest request, @RequestParam CommonsMultipartFile[] fileUpload)
    {
        if(fileUpload == null || fileUpload.length == 1)
        {
            System.out.println(fileUpload[0].getOriginalFilename());
            System.out.println(fileUpload[0].getName());
            System.out.println(fileUpload[0].getStorageDescription());
            
        }
            
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("soureDocumentForm", sourceDocumentForm);
            mm.addAttribute(model);
            
            return new ModelAndView("sourcedocument_create",mm);
        }
        else
        {
            
            return new ModelAndView("redirect:/sourcedocument/list/");
        }
    }
    
}
