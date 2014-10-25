package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.ConfigurationDAO;
import cz.muni.fi.mir.db.domain.Configuration;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
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
}
