package cz.muni.fi.mir.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.AnnotationService;
import cz.muni.fi.mir.db.service.CanonicOutputService;
import cz.muni.fi.mir.db.service.FormulaService;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;

@Controller
@RequestMapping(value = "/annotation")
public class AnnotationController
{
    @Autowired
    AnnotationService annotationService;
    @Autowired
    CanonicOutputService canonicOutputService;
    @Autowired
    FormulaService formulaService;
    @Autowired
    SecurityContextFacade securityContext;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AnnotationController.class);

    @Secured("ROLE_USER")
    @RequestMapping(value={"/delete/{id}","/delete/{id}/"}, method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteAnnotation(@PathVariable Long id, HttpServletRequest request)
    {
        Annotation annotation = annotationService.getAnnotationByID(id);
        if (annotation == null ||
                (!request.isUserInRole("ROLE_ADMINISTRATOR") && !annotation.getUser().getUsername().equals(securityContext.getLoggedUser())))
        {
            logger.info(String.format("Blocked unauthorized deletion of annotation %d triggered by user %s.", id, securityContext.getLoggedUser()));
            return false;
        }
        CanonicOutput canonicOutput = canonicOutputService.getCanonicOutputByAnnotation(annotation);
        Formula formula = formulaService.getFormulaByAnnotation(annotation);
        if (canonicOutput != null)
        {
            canonicOutputService.deleteAnnotationFromCanonicOutput(canonicOutput, annotation);
        }
        else if (formula != null)
        {
            formulaService.deleteAnnotationFromFormula(formula, annotation);
        } else {
            return false;
        }
        return true;
    }
}
