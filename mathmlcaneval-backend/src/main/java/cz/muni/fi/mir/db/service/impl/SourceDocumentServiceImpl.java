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
@Service(value = "sourceDocumentService")
public class SourceDocumentServiceImpl implements SourceDocumentService
{
    @Autowired SourceDocumentDAO sourceDocumentDAO;
    
    @Override
    @Transactional(readOnly = false)
    public void createSourceDocument(SourceDocument sourceDocument)
    {
        sourceDocumentDAO.create(sourceDocument);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSourceDocument(SourceDocument sourceDocument)
    {
        sourceDocumentDAO.update(sourceDocument);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteSourceDocument(SourceDocument sourceDocument)
    {
        sourceDocumentDAO.delete(sourceDocument.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public SourceDocument getSourceDocumentByID(Long id)
    {
        return sourceDocumentDAO.getByID(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SourceDocument> getAllDocuments()
    {
        return sourceDocumentDAO.getAllDocuments();
    }
}
