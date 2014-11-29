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
package cz.muni.fi.mir.mathmlevaluation.db.dao;

import java.io.Serializable;

/**
 * Generic interface for DAO CRUD operations.
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T> Type of entity managed by this class
 * @param <PK> primary key of entity
 */
public interface GenericDAO<T, PK extends Serializable>
{

    /**
     * Method persist given input.
     *
     * @param t to be stored
     */
    void create(T t);

    /**
     * Method updates given input
     *
     * @param t to be updated
     */
    void update(T t);

    /**
     * Method fetches entity with given id
     *
     * @param id of entity to be fetched
     * @return entity with given id, null if there is no match.
     */
    T getByID(PK id);

    /**
     * Method deletes entity with given id.
     *
     * @param id
     */
    void delete(PK id);
}
