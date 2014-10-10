/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.audit;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.Formula;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Aspect
@Component
public class FormulaAuditor
{
    @Autowired private AuditorService auditorService;
    @Autowired private DatabaseEventFactory databaseEventFactory;
    
    
    
    @Before("execution(* cz.muni.fi.mir.db.service.FormulaService.annotateFormula(..)) && args(formula,annotation)")
    public void arroundCreateAnnotation(Formula formula, Annotation annotation)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.UPDATE, 
                        formula, 
                        "Annotated formula with " + annotation.getNote()
                )
        );
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.FormulaService.deleteAnnotationFromFormula(..)) && args(formula,annotation)")
    public void arroundDeleteAnnotation(Formula formula, Annotation annotation)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE,
                        formula, 
                        "Deleted annotation " + annotation.getNote() + " from formula"
                )
        );
    }
}
