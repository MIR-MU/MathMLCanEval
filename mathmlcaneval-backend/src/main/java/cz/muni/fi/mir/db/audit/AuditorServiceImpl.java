/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.audit;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */

@Service
@Transactional
public class AuditorServiceImpl implements AuditorService
{
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(AuditorServiceImpl.class);
    
    @Override
    public void createDatabaseEvent(DatabaseEvent databaseEvent)
    {        
        entityManager.persist(databaseEvent);
    }

    @Override
    public List<DatabaseEvent> getLatestEvents()
    {
        List<DatabaseEvent> result = new ArrayList<>();
        
        try
        {
            result = entityManager.createQuery("SELECT de FROM databaseEvent de ORDER BY de.id DESC", DatabaseEvent.class)
                    .setMaxResults(40).getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return result;
    }
    
}