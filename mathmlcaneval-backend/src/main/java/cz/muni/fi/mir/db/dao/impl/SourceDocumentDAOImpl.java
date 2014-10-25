/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.SourceDocumentDAO;
import cz.muni.fi.mir.db.domain.SourceDocument;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "sourceDocumentDAO")
public class SourceDocumentDAOImpl extends GenericDAOImpl<SourceDocument, Long> implements SourceDocumentDAO
{    
    private static final Logger logger = Logger.getLogger(SourceDocumentDAOImpl.class);

    public SourceDocumentDAOImpl()
    {
        super(SourceDocument.class);
    }
    
    @Override
    public List<SourceDocument> getAllDocuments()
    {
        List<SourceDocument> resultList = Collections.emptyList();
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
}
