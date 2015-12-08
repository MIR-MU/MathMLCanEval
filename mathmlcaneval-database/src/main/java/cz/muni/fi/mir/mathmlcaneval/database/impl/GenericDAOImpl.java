/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database.impl;

import cz.muni.fi.mir.mathmlcaneval.database.GenericDAO;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T>
 * @param <PK>
 */
public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK>
{
    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> type;
    
    public GenericDAOImpl(Class<T> type)
    {
        this.type = type;
    }

    @Override
    public void create(T t)
    {
        this.entityManager.persist(t);
    }

    @Override
    public void update(T t)
    {
        this.entityManager.merge(t);
    }

    @Override
    public T getByID(PK pk)
    {
        return entityManager.find(type, pk);
    }

    @Override
    public void delete(PK pk)
    {
        T deletable = getByID(pk);
        if(deletable != null)
        {
            this.entityManager.remove(deletable);
        }
    }
    
}
