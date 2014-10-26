/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.db.audit;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;

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
    public void aroundCreateAnnotation(CanonicOutput canonicOutput, Annotation annotation)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.UPDATE, 
                        canonicOutput, 
                        "Annotated canonicoutput with " + annotation.getAnnotationContent()
                )
        );
    }

    @Before("execution(* cz.muni.fi.mir.db.service.CanonicOutputService.deleteAnnotationFromCanonicOutput(..)) && args(canonicOutput,annotation)")
    public void aroundDeleteAnnotation(CanonicOutput canonicOutput, Annotation annotation)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE,
                        canonicOutput, 
                        "Deleted annotation " + annotation.getAnnotationContent() + " from canonicoutput"
                )
        );
    }
}
