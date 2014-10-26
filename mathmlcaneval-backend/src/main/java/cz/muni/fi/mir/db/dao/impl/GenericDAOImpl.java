/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.db.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.mir.db.dao.GenericDAO;

/**
 * Generic implementation of DAO class for basic CRUD operations.
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T> type of entity
 * @param <PK> primary key of entity
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
        if (t != null)
        {
            entityManager.remove(t);
        }
    }
}
