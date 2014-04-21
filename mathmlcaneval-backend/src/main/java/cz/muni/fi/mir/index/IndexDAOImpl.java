/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.index;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emptak
 */
@Repository(value = "indexDAO")
public class IndexDAOImpl implements IndexDAO
{
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(IndexDAOImpl.class);
    
    
    public void init()
    {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        try
        {
            fullTextEntityManager.createIndexer().start();
        }
        catch(Exception e)
        {
            logger.fatal(e);
        }
    }

    @Override
    public void create(IndexedOutput indexedOutput)
    {
        logger.info("Index persist");
        indexedOutput.setCountForm(indexedOutput.getMapCountForm().toString());
        entityManager.persist(indexedOutput);
    }

}
