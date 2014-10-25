/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.SourceDocument;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface SourceDocumentService
{
    /**
     * 
     * @param sourceDocument
     * @throws IllegalArgumentException 
     */
    void createSourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;
    
    /**
     * 
     * @param sourceDocument
     * @throws IllegalArgumentException 
     */
    void updateSourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;
    
    /**
     * 
     * @param sourceDocument
     * @throws IllegalArgumentException 
     */
    void deleteSourceDocument(SourceDocument sourceDocument) throws IllegalArgumentException;
    
    /**
     * 
     * @param id
     * @return
     * @throws IllegalArgumentException 
     */
    SourceDocument getSourceDocumentByID(Long id) throws IllegalArgumentException;
    
    /**
     * 
     * @return 
     */
    List<SourceDocument> getAllDocuments();
}
