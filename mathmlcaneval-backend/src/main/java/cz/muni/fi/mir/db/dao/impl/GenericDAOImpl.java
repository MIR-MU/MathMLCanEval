/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao.impl;

import cz.muni.fi.mir.db.dao.GenericDAO;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;


public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK>
{
    private final Class<T> type;

    public GenericDAOImpl(Class<T> type)
    {
        this.type = type;
    }
    
    private static final Logger logger = Logger.getLogger(GenericDAOImpl.class);
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void create(T t)
    {
        entityManager.persist(t);
    }

    @Override
    public void update(T t)
    {
        entityManager.merge(t);
    }

    @Override
    public T getByID(PK id)
    {
        return entityManager.find(type, id);
    }

    @Override
    public void delete(PK id)
    {
        T t = getByID(id);
        if(t != null)
        {
            entityManager.remove(t);
        }
    }    
}
