/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.dao.impl;

import cz.muni.fi.mir.dao.SourceDocumentDAO;
import cz.muni.fi.mir.domain.SourceDocument;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "sourceDocumentDAO")
public class SourceDocumentDAOImpl implements SourceDocumentDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SourceDocumentDAOImpl.class);

    @Override
    public void createSourceDocument(SourceDocument sourceDocument)
    {
        entityManager.persist(sourceDocument);
    }

    @Override
    public void updateSourceDocument(SourceDocument sourceDocument)
    {
        entityManager.merge(sourceDocument);
    }

    @Override
    public void deleteSourceDocument(SourceDocument sourceDocument)
    {
        SourceDocument sd = entityManager.find(SourceDocument.class, sourceDocument.getId());
        if(sd != null)
        {
            entityManager.remove(sd);
        }
        else
        {
            logger.info("Trying to delete SourceDocument with ID that has not been found. The ID is ["+sourceDocument.getId().toString()+"]");
        }
    }

    @Override
    public SourceDocument getSourceDocumentByID(Long id)
    {
        return entityManager.find(SourceDocument.class, id);
    }

    @Override
    public SourceDocument getSourceDocumentByPath(String path)
    {
        SourceDocument sd = null;
        try
        {
            sd = entityManager.createQuery("SELECT sd FROM sourceDocument sd where sd.documentPath = :documentPath", SourceDocument.class)
                    .setParameter("documentPath", path).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return sd;
    }

    @Override
    public List<SourceDocument> getAllDocuments()
    {
        List<SourceDocument> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT sd FROM sourceDocument sd", SourceDocument.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<SourceDocument> getDocumentsOnSubPath(String subPath)
    {
        List<SourceDocument> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT sd FROM sourceDocument sd WHERE sd.documentPath LIKE :documentPath", SourceDocument.class)
                    .setParameter("documentPath", subPath+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
