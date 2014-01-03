/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.dao.impl;

import cz.muni.fi.mir.dao.ConfigurationDAO;
import cz.muni.fi.mir.domain.Configuration;
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
@Repository(value = "configurationDAO")
public class ConfigurationDAOImpl implements ConfigurationDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConfigurationDAOImpl.class);
    
    @Override
    public void createConfiguration(Configuration configuration)
    {
        entityManager.persist(configuration);
    }

    @Override
    public void updateConfiguration(Configuration configuration)
    {
        entityManager.merge(configuration);
    }

    @Override
    public void deleteConfiguration(Configuration configuration)
    {
        Configuration c = entityManager.find(Configuration.class, configuration.getId());
        if(c!= null)
        {
            entityManager.remove(c);
        }
        else
        {
            logger.info("Trying to delete Configuration with ID that has not been found. The ID is ["+configuration.getId().toString()+"]");
        }
    }

    @Override
    public Configuration getConfigurationByID(Long id)
    {
        return entityManager.find(Configuration.class, id);
    }

    @Override
    public List<Configuration> getAllCofigurations()
    {
        List<Configuration> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT c FROM configuration c", Configuration.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Configuration> getBySubstringNote(String subString)
    {
        List<Configuration> resultList = new ArrayList<>();
        
        try
        {
            resultList = entityManager.createQuery("SELECT c FROM configuration c WHERE c.note LIKE :subString", Configuration.class)
                    .setParameter("subString", "%"+subString+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }

    @Override
    public List<Configuration> findyByName(String name)
    {
        List<Configuration> resultList = new ArrayList<>();
        try
        {
            resultList = entityManager.createQuery("SELECT c FROM configuration c WHERE c.name LIKE :name", Configuration.class)
                    .setParameter("name", "%"+name+"%").getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
    
}
