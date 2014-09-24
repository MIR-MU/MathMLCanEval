/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Element;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.service.AnnotationService;
import cz.muni.fi.mir.db.service.ApplicationRunService;
import cz.muni.fi.mir.db.service.ConfigurationService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.db.service.RevisionService;
import cz.muni.fi.mir.db.service.SourceDocumentService;
import cz.muni.fi.mir.db.service.UserService;
import cz.muni.fi.mir.forms.ApplicationRunForm;
import cz.muni.fi.mir.forms.FindSimilarForm;
import cz.muni.fi.mir.forms.FormulaForm;
import cz.muni.fi.mir.pagination.Pagination;
import cz.muni.fi.mir.services.MathCanonicalizerLoader;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.db.domain.FormulaSearchRequest;
import cz.muni.fi.mir.db.domain.FormulaSearchResponse;
import cz.muni.fi.mir.db.service.ElementService;
import cz.muni.fi.mir.forms.ElementFormRow;
import cz.muni.fi.mir.forms.FormulaSearchRequestForm;
import cz.muni.fi.mir.tools.SiteTitle;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author siska
 */
@Controller
@RequestMapping(value = "/formula")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
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
    @Autowired
    private ElementService elementService;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FormulaController.class);

    @RequestMapping(value = {"/create", "/create/"}, method = RequestMethod.GET)
    @SiteTitle("{entity.formula.create}")
    public ModelAndView createFormula()
    {
        ModelMap mm = prepareModelMap(true, true, true, true,false);        
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
    @SiteTitle("{entity.formula.create}")
    public ModelAndView createFormulaSubmit(@Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, 
            BindingResult result, 
            Model model) throws IOException
    {
        if (result.hasErrors())
        {
            ModelMap mm = prepareModelMap(true, true, true, true,false);            
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute(model);

            return new ModelAndView("formula_create", mm);
        } 
        else
        {  
            if (!StringUtils.isBlank(formulaForm.getXml()))
            {
                // canonicalize on import
                if (formulaForm.getRevisionForm() != null && formulaForm.getConfigurationForm() != null)
                {
                    formulaService.simpleFormulaImport(formulaForm.getXml(), 
                            mapper.map(formulaForm.getRevisionForm(), Revision.class), 
                            mapper.map(formulaForm.getConfigurationForm(), Configuration.class), 
                            mapper.map(formulaForm.getProgramForm(),Program.class), 
                            mapper.map(formulaForm.getSourceDocumentForm(),SourceDocument.class),
                            securityContext.getLoggedEntityUser());
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

                        // canonicalize on import
                        if (formulaForm.getRevisionForm() != null && formulaForm.getConfigurationForm() != null)
                        {
                            //TODO will we really ever upload more than 1 file ?
                            formulaService.simpleFormulaImport(formulaForm.getXml(), 
                                mapper.map(formulaForm.getRevisionForm(), Revision.class), 
                                mapper.map(formulaForm.getConfigurationForm(), Configuration.class), 
                                mapper.map(formulaForm.getProgramForm(),Program.class), 
                                mapper.map(formulaForm.getSourceDocumentForm(),SourceDocument.class),
                                securityContext.getLoggedEntityUser());
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
    @SiteTitle("{entity.formula.view}")
    public ModelAndView viewFormula(@PathVariable Long id)
    {
        ModelMap mm = prepareModelMap(true, true, false, false,false);
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

        mathCanonicalizerLoader.execute(Arrays.asList(formula), applicationRun);
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
    @SiteTitle("{entity.formula.list}")
    public ModelAndView listPage(@ModelAttribute("pagination") Pagination pagination, Model model)
    {
        if (pagination.getNumberOfRecords() == 0) {
            pagination.setNumberOfRecords(formulaService.getNumberOfRecords());
        }

        ModelMap mm = prepareModelMap(true, true, true, true,true);
        mm.addAttribute("pagination", pagination);
        mm.addAttribute("formulaList", formulaService.getAllFormulas(pagination.getPageSize() * (pagination.getPageNumber() - 1), pagination.getPageSize()));
        mm.addAttribute("formulaSearchRequestForm", new FormulaSearchRequestForm());

        return new ModelAndView("formula_list",mm);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value={"/mass","/mass/"},method = RequestMethod.GET)
    @SiteTitle("{navigation.import.mass}")
    public ModelAndView massImport()
    {
        ModelMap mm = prepareModelMap(true,true,true,true,false);
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
    @SiteTitle("{navigation.import.mass}")
    public ModelAndView submitMassImport(@RequestParam(value = "importPath") String path,
            @RequestParam(value = "filter") String filter,
            @Valid @ModelAttribute("formulaForm") FormulaForm formulaForm, 
            BindingResult result, 
            Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = prepareModelMap(true,true,true,true,false);
            mm.addAttribute("formulaForm", formulaForm);
            mm.addAttribute(model);
            
            return new ModelAndView("formula_create_mass", mm); 
        }
        else
        {
            // @Async call
            formulaService.massFormulaImport(path, filter, 
                    mapper.map(formulaForm.getRevisionForm(), Revision.class), 
                    mapper.map(formulaForm.getConfigurationForm(), Configuration.class), 
                    mapper.map(formulaForm.getProgramForm(), Program.class),
                    mapper.map(formulaForm.getSourceDocumentForm(), SourceDocument.class),
                    securityContext.getLoggedEntityUser());
            
            return new ModelAndView("redirect:/formula/list/");
        }
    }
    
    @RequestMapping(value = {"/annotate/","/annotate"},
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String annotate(@RequestParam(value = "formulaID") Long formulaID, @RequestParam(value = "annotation-value") String annotation)
    {
        User u = securityContext.getLoggedEntityUser();
        Annotation a = EntityFactory.createAnnotation(annotation, u);
        
        formulaService.annotateFormula(formulaService.getFormulaByID(formulaID), a);
        
        return "{ \"user\": \""+u.getUsername()+"\", \"note\" : \""+annotation+"\"}";
    }
    
    private ModelMap prepareModelMap(boolean includeRevision, 
            boolean includeConfiguration, 
            boolean includeSourceDocument, 
            boolean includePrograms,
            boolean includeElements)
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
        
        if(includeElements)
        {
            mm.addAttribute("elementList", elementService.getAllElements());
        }
        
        return mm;
    }
    
    @RequestMapping(value = {"/reindex/","/reindex"},method = RequestMethod.GET)
    public ModelAndView reindex()
    {
        formulaService.reindex();
        
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value = {"/similar/","/similar"},method = RequestMethod.POST)
    @SiteTitle("{entity.canonicOutput.findSimilar}")
    public ModelAndView submitFindSimilar(@ModelAttribute(value = "findSimilarForm") FindSimilarForm form)
    {        
        Formula requestFormula = formulaService.getFormulaByID(form.getFormulaID());
        List<Formula> similars = formulaService.findSimilar(
                requestFormula, 
                generateSimilarityProperties(form),
                form.isOverride(),
                form.isDirectWrite()
        );
        
        ModelMap mm = new ModelMap();
        mm.addAttribute("similarForms", similars);
        mm.addAttribute("requestFormula", requestFormula);
        
        if(form.isDirectWrite())
        {
            return new ModelAndView("redirect:/formula/view/"+requestFormula.getId());
        }
        else
        {
            return new ModelAndView("find_similar_checkout",mm);
        }        
    }
    
    
    @RequestMapping(value = {"/submitsimilar/","/submitsimilar"},method = RequestMethod.POST)
    @SiteTitle("{entity.canonicOutput.findSimilar}")
    public ModelAndView submitSimilarFormulas(@RequestParam(value = "similarFormulaID") String[] similarIds,
            @RequestParam(value = "requestFormula") String formulaID,
            @RequestParam(value = "overrideCurrent",defaultValue = "off") String overrideCurrent
    )
    {
        Long[] ids = new Long[similarIds.length];
        for(int i =0; i < similarIds.length;i++)
        {
            ids[i] = Long.valueOf(similarIds[i]);
        }
        
        formulaService.attachSimilarFormulas(formulaService.getFormulaByID(Long.valueOf(formulaID)), ids,checkBoxToBool(overrideCurrent));
        
        return new ModelAndView("redirect:/formula/view/"+formulaID);
    }
    
    @RequestMapping(value = {"/massdelete","/massdelete/"},method = RequestMethod.GET)
    @SiteTitle("{entity.formula.massdelete}")
    public ModelAndView massDelete(@ModelAttribute("pagination") Pagination pagination, Model model)
    {
        if (pagination.getNumberOfRecords() == 0) {
            pagination.setNumberOfRecords(formulaService.getNumberOfRecords());
        }

        ModelMap mm = new ModelMap();
        mm.addAttribute("pagination", pagination);
        mm.addAttribute("formulaList", formulaService.getAllFormulas(pagination.getPageSize() * (pagination.getPageNumber() - 1), pagination.getPageSize()));
        mm.addAttribute("massDelete", true);

        return new ModelAndView("formula_mass_delete",mm);
    }
    
    @RequestMapping(value={"/massdelete","/massdelete/"},method = RequestMethod.POST)
    public ModelAndView massDelete(@RequestParam(value = "formulaDeleteID") String[] formulaDeleteIDs)
    {
        List<Formula> toDelete = new ArrayList<>();
        for(String s : formulaDeleteIDs)
        {
            toDelete.add(EntityFactory.createFormula(Long.valueOf(s)));
        }
        
        formulaService.massRemove(toDelete);
        
        
        return new ModelAndView("redirect:/formula/massdelete/");        
    }
    
    @RequestMapping(value = {"/search/"})
    public ModelAndView search(@ModelAttribute FormulaSearchRequestForm formulaSearchRequestForm,@ModelAttribute("pagination") Pagination pagination)
    {
        ModelMap mm = prepareModelMap(true, true, true, true,true);
        FormulaSearchRequest request = mapper.map(formulaSearchRequestForm,FormulaSearchRequest.class);
        
        if(formulaSearchRequestForm.getElementRows() != null && !formulaSearchRequestForm.getElementRows().isEmpty())
        {
            Map<Element,Integer> map = new HashMap<>();
            for(ElementFormRow efr :formulaSearchRequestForm.getElementRows())
            {
                map.put(mapper.map(efr.getElement(),Element.class), efr.getValue());
            }
            request.setElements(map);
        }
        
        FormulaSearchResponse response = formulaService.findFormulas(request);
        
        mm.addAttribute("formulaList", response.getFormulas());
        mm.addAttribute("pagination", pagination);
        mm.addAttribute("formulaSearchRequestForm", formulaSearchRequestForm);

        logger.info(request);
        logger.info(formulaSearchRequestForm);
        
        return new ModelAndView("formula_list",mm);
    }
    
    
    
    private Map<String,String> generateSimilarityProperties(FindSimilarForm findSimilarForm)
    {
        Map<String,String> properties = new HashMap<>();
        //booleans
        properties.put(FormulaService.USE_DISTANCE, Boolean.toString(findSimilarForm.isUseDistance()));
        properties.put(FormulaService.USE_COUNT,Boolean.toString(findSimilarForm.isUseCount()));
        properties.put(FormulaService.USE_BRANCH, Boolean.toString(findSimilarForm.isUseBranch()));
        properties.put(FormulaService.USE_OVERRIDE,Boolean.toString(findSimilarForm.isOverride()));
        // conditions
        properties.put(FormulaService.CONDITION_DISTANCE, findSimilarForm.getDistanceCondition());
        properties.put(FormulaService.CONDITION_COUNT, findSimilarForm.getCountCondition());
        properties.put(FormulaService.CONDITION_BRANCH,findSimilarForm.getBranchCondition());
        // actual evaluation values
        properties.put(FormulaService.VALUE_DISTANCEMETHOD,findSimilarForm.getDistanceMethodValue());
        properties.put(FormulaService.VALUE_COUNTELEMENTMETHOD,findSimilarForm.getCountElementMethodValue());
        properties.put(FormulaService.VALUE_BRANCHMETHOD,findSimilarForm.getBranchMethodValue());
        properties.put(FormulaService.DIRECT_WRITE,Boolean.toString(findSimilarForm.isDirectWrite()));
        
        return properties;
    }
    
    
    /**
     * Method converts checkbox default html value into boolean. if value is on then 
     * output is true, otherwise input should be off which is false
     * @param value to be converted
     * @return boolean value mapped as follows {on->true, off->false}
     * @throws IllegalArgumentException if input is neither <i>on</i> or <i>off</i>
     */
    private boolean checkBoxToBool(String value) throws IllegalArgumentException
    {
        switch (value)
        {
            case "on":
                return true;
            case "off":
                return false;
            default:
                throw new IllegalArgumentException("Wrong input expecting [on/off] was ["+value+"]");
        }
    }
}
