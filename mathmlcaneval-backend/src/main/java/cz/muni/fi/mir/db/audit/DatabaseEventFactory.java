/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.audit;

import cz.muni.fi.mir.db.domain.Auditable;
import cz.muni.fi.mir.wrappers.SecurityContextFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
