/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.SourceDocument;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface SourceDocumentDAO extends GenericDAO<SourceDocument,Long>
{    
    List<SourceDocument> getAllDocuments();
}
