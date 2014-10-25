/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.RevisionDAO;
import cz.muni.fi.mir.db.domain.Revision;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "revisionDAO")
public class RevisionDAOImpl extends GenericDAOImpl<Revision,Long> implements RevisionDAO
{    
    private static final Logger logger = Logger.getLogger(RevisionDAOImpl.class);

    public RevisionDAOImpl()
    {
        super(Revision.class);
    }

    @Override
    public Revision getRevisionByHash(String hash)
    {
        Revision result = null;
        try
        {
            result = entityManager.createQuery("SELECT r FROM revision r where r.revisionHash = :revision",Revision.class)
                    .setParameter("revision", hash).getSingleResult();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return result;
    }

    @Override
    public List<Revision> getAllRevisions()
    {
        List<Revision> result = Collections.emptyList();
        
        try
        {
            result = entityManager.createQuery("SELECT r FROM revision r", Revision.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return result;
    }    

    @Override
    public List<Revision> findRevisionByNote(String note)
    {
        List<Revision> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT r FROM revision r WHERE r.note LIKE :note", Revision.class)
                    .setParameter("note", "%"+note+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}