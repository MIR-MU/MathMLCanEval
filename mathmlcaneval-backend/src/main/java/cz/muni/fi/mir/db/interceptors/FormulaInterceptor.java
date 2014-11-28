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
package cz.muni.fi.mir.db.interceptors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationValue;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.service.AnnotationValueSerivce;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Aspect
@Component
public class FormulaInterceptor
{
    @Autowired private DatabaseEventService databaseEventService;
    @Autowired private AnnotationValueSerivce annotationValueSerivce;
    @Autowired private DatabaseEventFactory databaseEventFactory;
    private static final Pattern pattern = Pattern.compile("(#\\S+)");
    
    
    
    @Before("execution(* cz.muni.fi.mir.db.service.FormulaService.annotateFormula(..)) && args(formula,annotation)")
    public void aroundCreateAnnotation(Formula formula, Annotation annotation)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.UPDATE, 
                        formula, 
                        "Annotated formula with " + annotation.getAnnotationContent()
                )
        );
        
        Matcher m = pattern.matcher(annotation.getAnnotationContent());    
                
        while(m.find())
        {
            String match = m.group();
            AnnotationValue aValue = annotationValueSerivce.getAnnotationValueByValue(match);
            if(aValue == null)
            {
                aValue = new AnnotationValue();
                aValue.setValue(match);
                aValue.setType(AnnotationValue.Type.FORMULA);
                
                annotationValueSerivce.createAnnotationValue(aValue);
            }
        }
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.FormulaService.deleteAnnotationFromFormula(..)) && args(formula,annotation)")
    public void aroundDeleteAnnotation(Formula formula, Annotation annotation)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE,
                        formula, 
                        "Deleted annotation " + annotation.getAnnotationContent() + " from formula"
                )
        );
    }
}
