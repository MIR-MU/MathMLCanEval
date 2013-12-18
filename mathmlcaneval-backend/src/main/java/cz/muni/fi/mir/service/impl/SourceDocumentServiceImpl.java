/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.service.impl;

import cz.muni.fi.mir.dao.SourceDocumentDAO;
import cz.muni.fi.mir.domain.SourceDocument;
import cz.muni.fi.mir.service.SourceDocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Empt
 */
@Service(value = "sourceService")
public class SourceDocumentServiceImpl implements SourceDocumentService
{
    @Autowired SourceDocumentDAO sourceDocumentDAO;
    
    @Override
    @Transactional(readOnly = false)
    public void createSourceDocument(SourceDocument sourceDocument)
    {
        sourceDocumentDAO.createSourceDocument(sourceDocument);
    }

    @Override
    public void updateSourceDocument(SourceDocument sourceDocument)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSourceDocument(SourceDocument sourceDocument)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SourceDocument getSourceDocumentByID(Long id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SourceDocument getSourceDocumentByPath(String path)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SourceDocument> getAllDocuments()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SourceDocument> getDocumentsOnSubPath(String subPath)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
