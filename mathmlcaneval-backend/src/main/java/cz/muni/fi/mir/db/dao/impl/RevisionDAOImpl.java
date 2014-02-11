/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.RevisionDAO;
import cz.muni.fi.mir.db.domain.Revision;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Empt
 */
@Repository(value = "revisionDAO")
public class RevisionDAOImpl implements RevisionDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RevisionDAOImpl.class);

    @Override
    public void createRevision(Revision revision)
    {
        entityManager.persist(revision);
    }

    @Override
    public void deleteRevision(Revision revision)
    {
        Revision r = entityManager.find(Revision.class, revision.getId());
        if(r != null)
        {
            entityManager.remove(r);
        }
        else
        {
            logger.info("Trying to delete Revision with ID that has not been found. The ID is ["+revision.getId().toString()+"]");
        }
    }

    @Override
    public void updateRevision(Revision revision)
    {
        entityManager.merge(revision);
    }

    @Override
    public Revision getRevisionByID(Long id)
    {
        return entityManager.find(Revision.class, id);
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
