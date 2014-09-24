/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.ApplicationRunForm;
import cz.muni.fi.mir.pagination.Pagination;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.SiteTitle;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author emptak
 */
@Controller
@RequestMapping("/appruns")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class ApplicationRunController
{
    
    @Autowired 
    private FormulaService formulaService;
    @Autowired 
    private ApplicationRunService applicationRunService;
    @Autowired
    private RevisionService revisionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private SourceDocumentService sourceDocumentService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private SecurityContextFacade securityContext;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MathCanonicalizerLoader mathCanonicalizerLoader;

    
    @RequestMapping(value = {"/","/list","/list/"},method = RequestMethod.GET)
    @SiteTitle("{entity.appruns.list}")
    public ModelAndView list()
    {
        ModelMap mm = prepareModelMap(false, false, false, false);
        mm.addAttribute("apprunList", applicationRunService.getAllApplicationRuns());
        
        
        return new ModelAndView("apprun_list", mm);
    }
    
    
    @RequestMapping(value = {"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteApplicationRun(@PathVariable Long id)
    {
        applicationRunService.deleteApplicationRun(applicationRunService.getApplicationRunByID(id),true,true);
        
        
        return new ModelAndView("redirect:/appruns/");
    }
    
    @RequestMapping(value = {"/create","/create/"},method = RequestMethod.GET)
    @SiteTitle("{entity.appruns.new}")
    public ModelAndView create()
    {
        ModelMap mm = prepareModelMap(true, true, true, true);
        mm.addAttribute("applicationRunForm", new ApplicationRunForm());
        mm.addAttribute("massCanonicalize", true);

        return new ModelAndView("apprun_create", mm);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.POST)
    @SiteTitle("{entity.appruns.new}")
    public ModelAndView submitCreate(@RequestParam(value = "formulaCanonicalizeID") String formulaCanonicalizeIDs, @Valid @ModelAttribute("applicationRunForm") ApplicationRunForm applicationRunForm, BindingResult result, Model model)
    {
        if(result.hasErrors() || StringUtils.isBlank(formulaCanonicalizeIDs))
        {
            ModelMap mm = prepareModelMap(true, true, true, true);
            mm.addAttribute("applicationRunForm", applicationRunForm);
            mm.addAttribute("massCanonicalize", true);
            mm.addAttribute(model);

            return new ModelAndView("apprun_create",mm);
        }
        else
        {
            List<Long> toCanonicalize = new ArrayList<>();
            for (String formulaID : Arrays.asList(formulaCanonicalizeIDs.split(" ")))
            {
                toCanonicalize.add(Long.valueOf(formulaID));
            }

            // @Async call            
            formulaService.massCanonicalize(toCanonicalize,
                    mapper.map(applicationRunForm.getRevisionForm(), Revision.class),
                    mapper.map(applicationRunForm.getConfigurationForm(), Configuration.class),
                    securityContext.getLoggedEntityUser());

            return new ModelAndView("redirect:/dashboard/");
        }
    }

    @RequestMapping(value = {"/create/select","/create/select/"},method = RequestMethod.GET)
    @SiteTitle("{entity.appruns.new}")
    public ModelAndView createSelect(@ModelAttribute("pagination") Pagination pagination, Model model)
    {
        if (pagination.getNumberOfRecords() == 0) {
            pagination.setNumberOfRecords(formulaService.getNumberOfRecords());
        }

        ModelMap mm = prepareModelMap(true, true, true, true);
        mm.addAttribute("applicationRunForm", new ApplicationRunForm());
        mm.addAttribute("pagination", pagination);
        mm.addAttribute("formulaList", formulaService.getAllFormulas(pagination.getPageSize() * (pagination.getPageNumber() - 1), pagination.getPageSize()));
        mm.addAttribute("massCanonicalize", true);
        
        return new ModelAndView("apprun_create_select", mm);
    }

    @RequestMapping(value={"/create/select","/create/select/"},method = RequestMethod.POST)
    public ModelAndView submitCreateSelect(@ModelAttribute("pagination") Pagination pagination, @RequestParam(value = "formulaCanonicalizeID") String[] formulaCanonicalizeIDs, @Valid @ModelAttribute("applicationRunForm") ApplicationRunForm applicationRunForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = prepareModelMap(true, true, true, true);
            mm.addAttribute("applicationRunForm", applicationRunForm);
            mm.addAttribute("pagination", pagination);
            mm.addAttribute("formulaList", formulaService.getAllFormulas(pagination.getPageSize() * (pagination.getPageNumber() - 1), pagination.getPageSize()));
            mm.addAttribute("massCanonicalize", true);
            mm.addAttribute(model);

            return new ModelAndView("apprun_create_select",mm);
        } else {
            List<Long> toCanonicalize = new ArrayList<>();
            for(String formulaID : formulaCanonicalizeIDs)
            {
                if (!StringUtils.isBlank(formulaID)) {
                    toCanonicalize.add(Long.valueOf(formulaID));
                }
            }
            formulaService.massCanonicalize(toCanonicalize,
                    mapper.map(applicationRunForm.getRevisionForm(), Revision.class),
                    mapper.map(applicationRunForm.getConfigurationForm(), Configuration.class),
                    securityContext.getLoggedEntityUser());
        }
        return new ModelAndView("redirect:/dashboard/");
    }

    private ModelMap prepareModelMap(boolean includeRevision, boolean includeConfiguration, boolean includeSourceDocument, boolean includeProgram)
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
        if(includeProgram)
        {
            mm.addAttribute("programList", programService.getAllPrograms());
        }

        return mm;
    }
}
