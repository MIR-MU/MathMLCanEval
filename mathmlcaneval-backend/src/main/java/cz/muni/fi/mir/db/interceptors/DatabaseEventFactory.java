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

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.muni.fi.mir.db.domain.Auditable;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class DatabaseEventFactory
{
 
    @Autowired private SecurityContextFacade securityContextFacade;
    
    public DatabaseEvent newInstance(DatabaseEvent.Operation operation, Auditable target, String message)
    {
        DatabaseEvent de = new DatabaseEvent();
        de.setMessage(message);
        de.setOperation(operation);
        de.setTargetID(target.getId());
        de.setTargetClass(target.getClass().getSimpleName());
        de.setUser(securityContextFacade.getLoggedEntityUser());
        de.setEventTime(DateTime.now());
        
        return de;
    }
}
