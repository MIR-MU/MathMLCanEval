/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.audit;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;
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
public class CanonicOutputAuditor
{
    @Autowired private AuditorService auditorService;
    @Autowired private DatabaseEventFactory databaseEventFactory;
    
    @Before("execution(* cz.muni.fi.mir.db.service.CanonicOutputService.annotateCannonicOutput(..)) && args(canonicOutput,annotation)")
    public void arroundCreateAnnotation(CanonicOutput canonicOutput, Annotation annotation)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.UPDATE, 
                        canonicOutput, 
                        "Annotated canonicoutput with " + annotation.getAnnotationContent()
                )
        );
    }

    @Before("execution(* cz.muni.fi.mir.db.service.CanonicOutputService.deleteAnnotationFromCanonicOutput(..)) && args(canonicOutput,annotation)")
    public void arroundDeleteAnnotation(CanonicOutput canonicOutput, Annotation annotation)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE,
                        canonicOutput, 
                        "Deleted annotation " + annotation.getAnnotationContent() + " from canonicoutput"
                )
        );
    }
}
