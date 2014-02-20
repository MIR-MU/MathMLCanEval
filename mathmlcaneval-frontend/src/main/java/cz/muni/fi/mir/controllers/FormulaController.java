/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.domain.UploadedFile;
import cz.muni.fi.mir.forms.FormulaForm;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.dozer.Mapper;
import org.hibernate.annotations.common.util.impl.Log_$logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author siska
 */
@Controller
@RequestMapping(value = "/formula")
public class FormulaController {

    @Autowired
    private FormulaService formulaService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private UserService userService;
    @Autowired
    private SourceDocumentService sourceDocumentService;
    @Autowired
    private SecurityContextFacade securityContext;
    @Autowired
    private Mapper mapper;
    
    LinkedList<UploadedFile> uploadedFiles = new LinkedList<>();

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserController.class);

    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.GET)
    public ModelAndView createFormula() {
        ModelMap mm = new ModelMap();
        uploadedFiles.clear();
        mm.addAttribute("formulaForm", new FormulaForm());
        mm.addAttribute("programFormList", programService.getAllPrograms());
        mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());

        return new ModelAndView("formula_create", mm);
    }

    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.POST)
    public ModelAndView createFormulaSubmit(@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            ModelMap mm = new ModelMap();
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute(model);
            mm.addAttribute("programFormList", programService.getAllPrograms());
            mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());
            mm.addAttribute("uploadedFiles", uploadedFiles);

            return new ModelAndView("formula_create", mm);
        } else {
            String user = securityContext.getLoggedUser();

            formulaForm.setUserForm(mapper.map(userService.getUserByUsername(user), UserForm.class));
            formulaForm.setInsertTime(DateTime.now());
            if (uploadedFiles.isEmpty()) {
                formulaService.createFormula(mapper.map(formulaForm, Formula.class));
            }

            for (UploadedFile file : uploadedFiles) {
                try {
                    formulaForm.setXml(new String(file.getData(), "UTF-8"));

                    formulaService.createFormula(mapper.map(formulaForm, Formula.class));        
                } catch (UnsupportedEncodingException ex) {
                    // ignore wrong uploads
                    logger.info(ex);
                }
            }
            uploadedFiles.clear();

            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = {"/upload", "/upload/"}, method = RequestMethod.POST)
    public @ResponseBody List<UploadedFile> upload(MultipartHttpServletRequest request, HttpServletResponse response)
    {
        Iterator<String> fileIterator = request.getFileNames();
        MultipartFile file = null;

        while (fileIterator.hasNext())
        {
            file = request.getFile(fileIterator.next());

            UploadedFile u = new UploadedFile(file.getOriginalFilename(), Long.valueOf(file.getSize()).intValue());
            try {
                u.setData(file.getBytes());
                // TODO: check for valid mathml files

                uploadedFiles.add(u);
            } catch (IOException ex) {
                // ignore wrong uploads
                logger.info(ex);
            }
        }
        return uploadedFiles;
    }

    @RequestMapping(value = {"/view/{id}", "/view/{id}/"}, method = RequestMethod.GET)
    public ModelAndView viewFormula(@PathVariable Long id) {
        ModelMap mm = new ModelMap();
        mm.addAttribute("formulaEntry", formulaService.getFormulaByID(id));

        return new ModelAndView("formula_view", mm);
    }
}
