package cz.muni.fi.mir.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.service.ProgramService;
import cz.muni.fi.mir.forms.ProgramForm;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.SiteTitle;

/**
 * This class serves for handling requests for Program objects with requests starting with <b>/program</b>
 * path.
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value ="/program")
@SiteTitle(mainTitle = "{website.title}", separator = " - ")
public class ProgramController
{
    @Autowired private ProgramService programService;
    @Autowired private Mapper mapper;
    
    @RequestMapping(value={"/","/list","/list/"},method = RequestMethod.GET)
    @SiteTitle("{navigation.program.list}")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("programList", programService.getAllPrograms());
        
        return new ModelAndView("program_list",mm);
    }
    
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.GET)
    @SiteTitle("{navigation.program.create}")
    public ModelAndView createProgram()
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("programForm", new ProgramForm());
        
        return new ModelAndView("program_create",mm);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value={"/create","/create/"},method = RequestMethod.POST)
    @SiteTitle("{navigation.program.create}")
    public ModelAndView createProgramSubmit(@Valid @ModelAttribute("programForm") ProgramForm programForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("programForm", programForm);
            mm.addAttribute(model);
            
            return new ModelAndView("program_create",mm);
        }
        else
        {
            programService.createProgram(mapper.map(programForm,Program.class));
            return new ModelAndView("redirect:/program/list/");
        }
    }
    
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"},method = RequestMethod.GET)
    public ModelAndView deleteProgram(@PathVariable Long id)
    {
        programService.deleteProgram(EntityFactory.createProgram(id));
        
        return new ModelAndView("redirect:/program/list/");
    }
    
    @RequestMapping(value ={"/edit/{id}","/edit/{id}/"},method = RequestMethod.GET)
    @SiteTitle("{entity.program.edit}")
    public ModelAndView editProgram(@PathVariable Long id)
    {
        ModelMap mm = new ModelMap();
        mm.addAttribute("programForm", mapper.map(programService.getProgramByID(id),ProgramForm.class));
        
        return new ModelAndView("program_edit",mm);
    }
    
    @Secured("ROLE_ADMINISTRATOR")
    @RequestMapping(value={"/edit/","/edit/"}, method = RequestMethod.POST)
    @SiteTitle("{entity.program.edit}")
    public ModelAndView editProgramSubmit(@Valid @ModelAttribute("programForm") ProgramForm programForm, BindingResult result, Model model)
    {
        if(result.hasErrors())
        {
            ModelMap mm = new ModelMap();
            mm.addAttribute("programForm", programForm);
            mm.addAttribute(model);
            
            return new ModelAndView("program_edit",mm);
        }
        else
        {
            programService.updateProgram(mapper.map(programForm, Program.class));
            
            return new ModelAndView("redirect:/program/list/");
        }
    }
    
    @RequestMapping(value = {"/list/{filters}","/list/{filters}/"},method = RequestMethod.GET)
    @SiteTitle("{navigation.program.list}")
    public ModelAndView filterList(@MatrixVariable(pathVar = "filters") Map<String,List<String>> filters)
    {
        ModelMap mm = new ModelMap();
        if(filters.containsKey("name") && filters.containsKey("version"))
        {
            mm.addAttribute("programList", 
                    programService.getProgramByNameAndVersion(filters.get("name").get(0), filters.get("version").get(0)));
        }
        else
        {
            mm.addAttribute("programList", programService.getAllPrograms());
        }
        
        return new ModelAndView("program_list",mm);
    }
}
