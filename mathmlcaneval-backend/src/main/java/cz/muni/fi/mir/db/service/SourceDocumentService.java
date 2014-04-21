/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.SourceDocument;
import java.util.List;

/**
 *
 * @author Empt
 */
public interface SourceDocumentService
{
    void createSourceDocument(SourceDocument sourceDocument);
    void updateSourceDocument(SourceDocument sourceDocument);
    void deleteSourceDocument(SourceDocument sourceDocument);
    
    SourceDocument getSourceDocumentByID(Long id);
    List<SourceDocument> getSourceDocumentByPath(String path);
    
    
    List<SourceDocument> getAllDocuments();
}
