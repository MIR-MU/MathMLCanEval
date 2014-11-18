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

import cz.muni.fi.mir.db.domain.Program;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
//@Aspect
//@Component
public class ProgramAuditor
{
    @Autowired private AuditorService auditorService;
    @Autowired private DatabaseEventFactory databaseEventFactory;
    
    @After("execution(* cz.muni.fi.mir.db.service.ProgramService.createProgram(..)) && args(program)")
    public void aroundCreateProgram(Program program)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.INSERT, 
                        program, 
                        "Created program "+program.getName()
                )
        );
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.ProgramService.deleteProgram(..)) && args(program)")
    public void aroundDeleteProgram(Program program)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE, 
                        program, 
                        "Deleted program "+program.getName()
                )
        );
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.ProgramService.updateProgram(..)) && args(program)")
    public void aroundUpdateProgram(Program program)
    {
        auditorService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.UPDATE, 
                        program, 
                        "Updated program "+program.getName()
                )
        );
    }
}
