package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.ConfigurationDAO;
import cz.muni.fi.mir.db.domain.Configuration;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * @author Dominik Szalai
 *
 * @version 1.0
 * @since 1.0
 *
 */
@Repository(value = "configurationDAO")
public class ConfigurationDAOImpl extends GenericDAOImpl<Configuration, Long> implements ConfigurationDAO
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConfigurationDAOImpl.class);  
    
    public ConfigurationDAOImpl()
    {
        super(Configuration.class);
    }
    
    @Override
    public List<Configuration> getAllCofigurations()
    {
        List<Configuration> resultList = Collections.emptyList();
        
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
        List<Configuration> resultList = Collections.emptyList();
        
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
        List<Configuration> resultList = Collections.emptyList();
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
