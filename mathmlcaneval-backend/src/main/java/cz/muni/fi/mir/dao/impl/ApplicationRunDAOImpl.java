/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.dao.impl;

import cz.muni.fi.mir.dao.ApplicationRunDAO;
import cz.muni.fi.mir.domain.ApplicationRun;
import cz.muni.fi.mir.domain.Configuration;
import cz.muni.fi.mir.domain.Revision;
import cz.muni.fi.mir.domain.User;
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
@Repository(value = "applicationRunDAO")
public class ApplicationRunDAOImpl implements ApplicationRunDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ApplicationRunDAOImpl.class);
    
    
    @Override
    public void createApplicationRun(ApplicationRun applicationRun)
    {
        entityManager.persist(applicationRun);
    }

    @Override
    public void updateApplicationRun(ApplicationRun applicationRun)
    {
        entityManager.merge(applicationRun);
    }

    @Override
    public void deleteApplicationRun(ApplicationRun applicationRun)
    {
        ApplicationRun ap = entityManager.find(ApplicationRun.class, applicationRun.getId());
        if(ap != null)
        {
            entityManager.remove(ap);
        }
        else
        {
            logger.info("Trying to delete ApplicationRun with ID that has not been found. The ID is ["+applicationRun.getId().toString()+"]");
        }
    }

    @Override
    public ApplicationRun getApplicationRunByID(Long id)
    {
        return entityManager.find(ApplicationRun.class, id);
    }

    @Override
    public List<ApplicationRun> getAllApplicationRuns()
    {
        List<ApplicationRun> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr", ApplicationRun.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsByUser(User user)
    {
        List<ApplicationRun> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr WHERE apr.user = :user", ApplicationRun.class)
                    .setParameter("user", user).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsByRevision(Revision revision)
    {
        List<ApplicationRun> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr WHERE apr.revision = :revision", ApplicationRun.class)
                    .setParameter("revision", revision).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<ApplicationRun> getAllApplicationRunsByConfiguration(Configuration configuration)
    {
        List<ApplicationRun> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT apr FROM applicationRun apr WHERE apr.configuration = :configuration", ApplicationRun.class)
                    .setParameter("configuration",configuration).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }    
}
