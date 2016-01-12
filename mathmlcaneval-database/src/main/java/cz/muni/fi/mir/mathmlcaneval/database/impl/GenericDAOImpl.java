/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database.impl;

import cz.muni.fi.mir.mathmlcaneval.database.GenericDAO;
import java.io.Serializable;
import java.util.List;
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
    protected final Class<T> type;
    private final String findAllQueryName;
    
    public GenericDAOImpl(Class<T> type, String findAllQueryName)
    {
        this.type = type;
        this.findAllQueryName = findAllQueryName;
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

    @Override
    public List<T> getAll()
    {
        return entityManager.createNamedQuery(findAllQueryName, type).getResultList();
    }    
}
