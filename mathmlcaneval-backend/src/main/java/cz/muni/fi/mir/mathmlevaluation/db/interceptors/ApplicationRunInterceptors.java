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
package cz.muni.fi.mir.mathmlevaluation.db.interceptors;

import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Aspect
@Component
public class ApplicationRunInterceptors
{

    @Autowired
    private DatabaseEventService databaseEventService;
    @Autowired
    private DatabaseEventFactory databaseEventFactory;
    private static final DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");

    /**
     * Tracks 'real' creation of application run. Create method is not tracked
     * because it only creates entry in database. Real functional create is made
     * after update is called because update sets end time.
     *
     * @param applicationRun
     */
    @After("execution(* cz.muni.fi.mir.mathmlevaluation.db.service.ApplicationRunService.updateApplicationRun(..)) && args(applicationRun)")
    public void aroundCreateApplicationRun(ApplicationRun applicationRun)
    {
        DatabaseEvent de = databaseEventFactory
                .newInstance(DatabaseEvent.Operation.INSERT,
                        applicationRun,
                        "Created application run " + applicationRun.getId() + " on " + dtfOut.print(applicationRun.getStopTime())
                );

        // because spring security is not propagated inside async calls somehow we have to set user manually
        de.setUser(applicationRun.getUser());
        databaseEventService.createDatabaseEvent(de);
    }

    @Before("execution(* cz.muni.fi.mir.mathmlevaluation.db.service.ApplicationRunService.createApplicationRun(..)) && args(applicationRun,deleteFormulas,deleteCanonicOutputs)")
    public void aroundDeleteApplicationRun(ApplicationRun applicationRun, boolean deleteFormulas, boolean deleteCanonicOutputs)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE,
                        applicationRun,
                        "Deleted application run " + applicationRun.getId()
                )
        );
    }
}
