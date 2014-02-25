/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.FormulaForm;
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.IOException;
import javax.validation.Valid;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author siska
 */
@Controller
@RequestMapping(value = "/formula")
public class FormulaController
{

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

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserController.class);

    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.GET)
    public ModelAndView createFormula()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("formulaForm", new FormulaForm());
        mm.addAttribute("programFormList", programService.getAllPrograms());
        mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());

        return new ModelAndView("formula_create", mm);
    }

    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.POST)
    public ModelAndView createFormulaSubmit(@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, BindingResult result, Model model) throws IOException
    {
        if (result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute("programFormList", programService.getAllPrograms());
            mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());
            mm.addAttribute(model);

            return new ModelAndView("formula_create", mm);
        } else
        {
            String user = securityContext.getLoggedUser();

            formulaForm.setUserForm(mapper.map(userService.getUserByUsername(user), UserForm.class));
            formulaForm.setInsertTime(DateTime.now());
            if (formulaForm.getUploadedFiles().isEmpty())
            {
                formulaService.createFormula(mapper.map(formulaForm, Formula.class));
            }

            for (MultipartFile file : formulaForm.getUploadedFiles())
            {
                try
                {
                    // check validity of file content
                    formulaForm.setXml(new String(file.getBytes(), "UTF-8"));

                    formulaService.createFormula(mapper.map(formulaForm, Formula.class));
                } catch (IOException ex)
                {
                    // ignore wrong uploads
                    logger.info(ex);
                }
            }

            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = {"/edit/{id}", "/edit/{id}/"}, method = RequestMethod.GET)
    public ModelAndView editFormula(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("formulaForm", mapper.map(formulaService.getFormulaByID(id), FormulaForm.class));
        mm.addAttribute("programFormList", programService.getAllPrograms());
        mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());

        return new ModelAndView("formula_edit", mm);
    }

    @RequestMapping(value = {"/edit", "/edit/"}, method = RequestMethod.POST)
    public ModelAndView editFormulaSubmit(@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, BindingResult result, Model model) throws IOException
    {
        if (result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute("programFormList", programService.getAllPrograms());
            mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());
            mm.addAttribute(model);

            return new ModelAndView("formula_edit", mm);
        } else
        {
            
            User user = userService.getUserByUsername(securityContext.getLoggedUser());
            if (user != null && formulaForm.getUserForm().getId().equals(user.getId())) {
                formulaForm.setInsertTime(DateTime.now());
                formulaService.updateFormula(mapper.map(formulaForm, Formula.class));
            }

            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = {"/view/{id}", "/view/{id}/"}, method = RequestMethod.GET)
    public ModelAndView viewFormula(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("formulaEntry", formulaService.getFormulaByID(id));

        return new ModelAndView("formula_view", mm);
    }
}
