/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Revision;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface RevisionDAO extends GenericDAO<Revision,Long>
{
    Revision getRevisionByHash(String hash);
    List<Revision> getAllRevisions();
    List<Revision> findRevisionByNote(String note);
}
