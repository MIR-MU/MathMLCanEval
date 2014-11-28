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

import cz.muni.fi.mir.db.domain.Configuration;
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
public class ConfigurationInterceptor
{
    @Autowired private DatabaseEventService databaseEventService;
    @Autowired private DatabaseEventFactory databaseEventFactory;
    
    @After("execution(* cz.muni.fi.mir.db.service.ConfigurationService.createConfiguration(..)) && args(configuration)")
    public void aroundCreateConfiguration(Configuration configuration)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.INSERT, 
                        configuration, 
                        "Created configuration "+configuration.getName()
                )
        );
    }
    
    @Before("execution(* cz.muni.fi.mir.db.service.ConfigurationService.deleteConfiguration(..)) && args(configuration)")
    public void aroundDeleteConfiguration(Configuration configuration)
    {
        databaseEventService.createDatabaseEvent(databaseEventFactory
                .newInstance(DatabaseEvent.Operation.DELETE, 
                        configuration, 
                        "Deleted configuration "+configuration.getName() //name can be used as it cannot be ever changed
                )
        );
    }
}
