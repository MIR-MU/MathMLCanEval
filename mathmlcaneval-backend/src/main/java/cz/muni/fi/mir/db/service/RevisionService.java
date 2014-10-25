/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.Revision;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface RevisionService
{
    
    void createRevision(Revision revision) throws IllegalArgumentException;
    void deleteRevision(Revision revision) throws IllegalArgumentException;
    void updateRevision(Revision revision) throws IllegalArgumentException;
    
    Revision getRevisionByID(Long id) throws IllegalArgumentException;    
    
    List<Revision> getAllRevisions(); 
}
