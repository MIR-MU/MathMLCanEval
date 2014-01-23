/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service.impl;

import cz.muni.fi.mir.db.dao.SourceDocumentDAO;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.service.SourceDocumentService;
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
    @Transactional(readOnly = false)
    public void updateSourceDocument(SourceDocument sourceDocument)
    {
        sourceDocumentDAO.updateSourceDocument(sourceDocument);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteSourceDocument(SourceDocument sourceDocument)
    {
        sourceDocumentDAO.deleteSourceDocument(sourceDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public SourceDocument getSourceDocumentByID(Long id)
    {
        return sourceDocumentDAO.getSourceDocumentByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SourceDocument getSourceDocumentByPath(String path)
    {
        return sourceDocumentDAO.getSourceDocumentByPath(path);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SourceDocument> getAllDocuments()
    {
        return sourceDocumentDAO.getAllDocuments();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SourceDocument> getDocumentsOnSubPath(String subPath)
    {
        return sourceDocumentDAO.getDocumentsOnSubPath(subPath);
    }
    
}
