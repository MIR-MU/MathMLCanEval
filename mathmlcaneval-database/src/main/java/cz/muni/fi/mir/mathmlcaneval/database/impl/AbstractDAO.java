/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database.impl;

import cz.muni.fi.mir.mathmlcaneval.database.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @param <T>
 * @param <PK>
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class AbstractDAO<T, PK extends Serializable> implements GenericDAO<T, PK>
{
    @PersistenceContext
    private EntityManager entityManager;
    private final String findAllQueryName;

    public AbstractDAO()
    {
        this.findAllQueryName = getClassType().getSimpleName() + ".getAll";
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
    public T getById(PK id)
    {
        return entityManager.find(getClassType(), id);
    }

    @Override
    public void delete(PK pk)
    {
        T goodbye = getById(pk);
        if (goodbye != null)
        {
            this.entityManager.remove(goodbye);
        }
    }

    @Override
    public List<T> getAll()
    {
        return entityManager.createNamedQuery(findAllQueryName, getClassType()).getResultList();
    }

    @Override
    public abstract Class<T> getClassType();

    protected EntityManager getEntityManager()
    {
        return entityManager;
    }
}
