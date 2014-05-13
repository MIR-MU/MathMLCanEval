/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
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
import cz.muni.fi.mir.forms.UserForm;
import cz.muni.fi.mir.pagination.Pagination;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;

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
        ModelMap mm = prepareModelMap(true, true, true, true);        
        mm.addAttribute("formulaForm", new FormulaForm());
        
        try
        {
            mathCanonicalizerLoader.jarFileExists();
        }
        catch(FileNotFoundException fnfe)
        {
            mm.addAttribute("jarFileErrorMessage", fnfe.getMessage());
        }
        

        return new ModelAndView("formula_create", mm);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.POST)
    public ModelAndView createFormulaSubmit(@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, BindingResult result, Model model) throws IOException
    {
        if (result.hasErrors())
        {
            ModelMap mm = prepareModelMap(true, true, true, true);            
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute(model);

            return new ModelAndView("formula_create", mm);
        } 
        else
        {   
            formulaForm.setUserForm(mapper.map(securityContext.getLoggedEntityUser(), UserForm.class));
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

    @RequestMapping(value = {"/view/{id}", "/view/{id}/"}, method = RequestMethod.GET)
    public ModelAndView viewFormula(@PathVariable Long id)
    {
        ModelMap mm = prepareModelMap(true, true, false, false);
        mm.addAttribute("formulaEntry", formulaService.getFormulaByID(id));
        mm.addAttribute("applicationRunForm", new ApplicationRunForm());

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
    public ModelAndView listPage(@ModelAttribute("pagination") Pagination pagination, Model model, @RequestParam(value = "pageNumber",required = false) Integer pageNumber)
    {
        if(pagination.isModified())
        {
            pagination = Pagination.newInstance(formulaService.getNumberOfRecords());
        }
        
        if(pageNumber != null)
        {
            pagination.setPageNumber(pageNumber);
        }
        
        ModelMap mm = new ModelMap();
        mm.addAttribute("pagination", pagination);
        mm.addAttribute("formulaList", formulaService.getAllFormulas(pagination.getPageSize() * (pagination.getPageNumber() - 1), pagination.getPageSize()));

        return new ModelAndView("formula_list",mm);
    }
    
    @RequestMapping(value={"/mass","/mass/"},method = RequestMethod.GET)
    public ModelAndView massImport()
    {
        ModelMap mm = prepareModelMap(true,true,true,true);
        mm.addAttribute("formulaForm", new FormulaForm());
        
        try
        {
            mathCanonicalizerLoader.jarFileExists();
        }
        catch(FileNotFoundException fnfe)
        {
            mm.addAttribute("jarFileErrorMessage", fnfe.getMessage());
        }
        

        return new ModelAndView("formula_create_mass", mm);        
    }
    
    @RequestMapping(value={"/mass","/mass/"},method = RequestMethod.POST)
    public ModelAndView submitMassImport(HttpServletRequest request,@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = prepareModelMap(true,true,true,true);
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute(model);
            
            return new ModelAndView("formula_create_mass", mm); 
        }
        else
        {
            String path = null;
            String filter = null;
            try
            {
                path = ServletRequestUtils.getStringParameter(request, "importPath");
                filter = ServletRequestUtils.getStringParameter(request, "filter");
            }
            catch(ServletRequestBindingException sre)
            {
                logger.error(sre);
            }
            
            
            formulaService.massFormulaImport(path, filter, 
                    securityContext.getLoggedEntityUser(),
                    mapper.map(formulaForm.getRevisionForm(), Revision.class), 
                    mapper.map(formulaForm.getConfigurationForm(), Configuration.class), 
                    mapper.map(formulaForm.getProgramForm(), Program.class),
                    mapper.map(formulaForm.getSourceDocumentForm(), SourceDocument.class));
            
            return new ModelAndView("redirect:/formula/list/");
        }
    }
    
    
    private ModelMap prepareModelMap(boolean includeRevision, boolean includeConfiguration, boolean includeSourceDocument, boolean includePrograms)
    {
        ModelMap mm = new ModelMap();
        if(includeRevision)
        {
            mm.addAttribute("revisionList", revisionService.getAllRevisions());
        }
        
        if(includeConfiguration)
        {
            mm.addAttribute("configurationList", configurationService.getAllCofigurations());
        }
        
        if(includeSourceDocument)
        {
            mm.addAttribute("sourceDocumentList", sourceDocumentService.getAllDocuments());
        }
        
        if(includePrograms)
        {
            mm.addAttribute("programList", programService.getAllPrograms());
        }
        
        return mm;
    }
}
