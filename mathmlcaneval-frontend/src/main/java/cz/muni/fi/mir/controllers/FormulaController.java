/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;

import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserService;

import cz.muni.fi.mir.forms.ApplicationRunForm;
import cz.muni.fi.mir.forms.FormulaForm;
import cz.muni.fi.mir.pagination.Pagination;
import cz.muni.fi.mir.forms.UserForm;

import cz.muni.fi.mir.services.FormulaCreator;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    
    @Autowired
    private FormulaCreator formulaCreator;

    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private RevisionService revisionService;
    @Autowired
    private ApplicationRunService applicationRunService;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FormulaController.class);

    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.GET)
    public ModelAndView createFormula()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("formulaForm", new FormulaForm());
        mm.addAttribute("programFormList", programService.getAllPrograms());
        mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());
        mm.addAttribute("revisionList", revisionService.getAllRevisions());
        mm.addAttribute("configurationList", configurationService.getAllCofigurations());

        return new ModelAndView("formula_create", mm);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.POST)
    public ModelAndView createFormulaSubmit(@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, BindingResult result, Model model) throws IOException
    {
        if (result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute("programFormList", programService.getAllPrograms());
            mm.addAttribute("sourceDocumentFormList", sourceDocumentService.getAllDocuments());
            mm.addAttribute("revisionList", revisionService.getAllRevisions());
            mm.addAttribute("configurationList", configurationService.getAllCofigurations());
            mm.addAttribute(model);

            return new ModelAndView("formula_create", mm);
        } else
        {
            String user = securityContext.getLoggedUser();

            formulaForm.setUserForm(mapper.map(userService.getUserByUsername(user), UserForm.class));
            formulaForm.setInsertTime(DateTime.now());
            if (!StringUtils.isBlank(formulaForm.getXml()))
            {
                Formula f = mapper.map(formulaForm, Formula.class);
                f.setOutputs(new ArrayList<CanonicOutput>());
                formulaService.createFormula(f);

                // canonicalize on import
                if (formulaForm.getRevisionForm() != null && formulaForm.getConfigurationForm() != null)
                {
                    ApplicationRun applicationRun = EntityFactory.createApplicationRun();
                    applicationRun.setUser(userService.getUserByUsername(securityContext.getLoggedUser()));
                    applicationRun.setRevision(mapper.map(formulaForm.getRevisionForm(), Revision.class));
                    applicationRun.setConfiguration(mapper.map(formulaForm.getConfigurationForm(), Configuration.class));
                    applicationRunService.createApplicationRun(applicationRun);

                    mathCanonicalizerLoader.execute(f, applicationRun);
                }
            }

            for (MultipartFile file : formulaForm.getUploadedFiles())
            {
                try
                {
                    // check validity of file content
                    if (file.getSize() > 0)
                    {
                        formulaForm.setXml(new String(file.getBytes(), "UTF-8"));
                        formulaForm.setInsertTime(DateTime.now());
                        Formula f = mapper.map(formulaForm, Formula.class);
                        f.setOutputs(new ArrayList<CanonicOutput>());
                        formulaService.createFormula(f);

                        // canonicalize on import
                        if (formulaForm.getRevisionForm() != null && formulaForm.getConfigurationForm() != null)
                        {
                            ApplicationRun applicationRun = EntityFactory.createApplicationRun();
                            applicationRun.setUser(userService.getUserByUsername(securityContext.getLoggedUser()));
                            applicationRun.setRevision(mapper.map(formulaForm.getRevisionForm(), Revision.class));
                            applicationRun.setConfiguration(mapper.map(formulaForm.getConfigurationForm(), Configuration.class));
                            applicationRunService.createApplicationRun(applicationRun);

                            mathCanonicalizerLoader.execute(f, applicationRun);
                        }
                    }
                } catch (IOException ex)
                {
                    // ignore wrong uploads
                    logger.info(ex);
                }
            }

            return new ModelAndView("redirect:/formula/list");
        }
    }
    
    @RequestMapping(value={"/create/sourcedocument/{sourceID}","/create/sourcedocument/{sourceID}/"},method = RequestMethod.GET)
    public ModelAndView createFormulaFromSourceDocument(@PathVariable Long sourceID)
    {
        SourceDocument sd = sourceDocumentService.getSourceDocumentByID(sourceID);
        List<Formula> formulas = null;
        try
        {
            formulas = formulaCreator.extractFormula(sd);
        }
        catch (IOException ex)
        {
            logger.error(ex);
        }
        
        for(Formula f : formulas)
        {
            formulaService.createFormula(f);
        }
        return new ModelAndView("redirect:/list/");
    }

    @RequestMapping(value = {"/view/{id}", "/view/{id}/"}, method = RequestMethod.GET)
    public ModelAndView viewFormula(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("formulaEntry", formulaService.getFormulaByID(id));
        mm.addAttribute("applicationRunForm", new ApplicationRunForm());
        mm.addAttribute("revisionList", revisionService.getAllRevisions());
        mm.addAttribute("configurationList", configurationService.getAllCofigurations());

        return new ModelAndView("formula_view", mm);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = {"/run", "/run/"}, method = RequestMethod.GET)
    public @ResponseBody void canonicalizeFormula(@RequestParam("formulaId") String formulaId, @ModelAttribute("applicationRunForm") ApplicationRunForm applicationRunForm) throws Exception
    {
        Formula formula = formulaService.getFormulaByID(new Long(formulaId));

        ApplicationRun applicationRun = new ApplicationRun();
        applicationRun.setUser(userService.getUserByUsername(securityContext.getLoggedUser()));
        applicationRun.setRevision(mapper.map(applicationRunForm.getRevisionForm(), Revision.class));
        applicationRun.setConfiguration(mapper.map(applicationRunForm.getConfigurationForm(), Configuration.class));
        applicationRunService.createApplicationRun(applicationRun);

        mathCanonicalizerLoader.execute(formula, applicationRun);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteFormula(@PathVariable Long id, HttpServletRequest request)
    {
        Formula f = formulaService.getFormulaByID(id);
        if (request.isUserInRole("ROLE_ADMINISTRATOR") || f.getUser().getUsername().equals(securityContext.getLoggedUser()))
        {
            formulaService.deleteFormula(f);
        } else
        {
            logger.info(String.format("Blocked unauthorized deletion of formula %d triggered by user %s.", id, securityContext.getLoggedUser()));
        }
        return new ModelAndView("redirect:/formula/list");
    }

    @RequestMapping(value = {"/list","/list/"},method = RequestMethod.GET)
    public ModelAndView listPage(@ModelAttribute("pagination") Pagination pagination, Model model)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("pagination", pagination);
        mm.addAttribute("formulaList", formulaService.getAllFormulas(pagination.getPageSize() * (pagination.getPageNumber() - 1), pagination.getPageSize()));

        return new ModelAndView("formula_list",mm);
    }
}
