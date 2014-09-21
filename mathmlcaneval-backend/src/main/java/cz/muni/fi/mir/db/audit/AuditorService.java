/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.audit;

import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AuditorService
{
    void createDatabaseEvent(DatabaseEvent databaseEvent);
    
    List<DatabaseEvent> getLatestEvents();
}