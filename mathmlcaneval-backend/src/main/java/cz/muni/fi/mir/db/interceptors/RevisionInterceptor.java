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

import cz.muni.fi.mir.db.domain.Revision;
import org.aspectj.lang.annotation.After;
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
public class RevisionInterceptor
{
    @Autowired private DatabaseEventService databaseEventService;
    @Autowired private DatabaseEventFactory databaseEventFactory;
    
    @After("execution(* cz.muni.fi.mir.db.service.RevisionService.createRevision(..)) && args(revision)")
    public void aroundCreateRevision(Revision revision)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.INSERT, 
                        revision, 
                        "Created revision "+revision.getRevisionHash()
                )
        );
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.RevisionService.deleteRevision(..)) && args(revision)")
    public void aroundDeleteRevision(Revision revision)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE, 
                        revision, 
                        "Deleted revision "+revision.getId()
                )
        );
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.RevisionService.updateRevision(..)) && args(revision)")
    public void aroundUpdateProgram(Revision revision)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.UPDATE, 
                        revision, 
                        "Updated revision "+revision.getId()
                )
        );
    }
}
