/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>
 * @param <PK>
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface GenericDAO<T, PK extends Serializable>
{
    /**
     * Method used for persisting entity of type T inside database
     *
     * @param entity to be stored
     */
    void create(T entity);

    /**
     * Method used for updating entity of type T inside database
     *
     * @param entity
     */
    void update(T entity);

    /**
     * Method used for fetching entity of type T with primary key PK
     *
     * @param id of entity
     * @return entity with given id, null if there is no such entity
     */
    T getById(PK id);

    /**
     * Method used for deleting entity with primary key PK
     *
     * @param id of entity to be removed
     */
    void delete(PK id);

    /**
     * Method returns all entries of type T
     *
     * @return all entries inside list of type T, null if there is no entries
     */
    List<T> getAll();

    /**
     * Method returns type of DAO
     *
     * @return type of DAO
     */
    Class<T> getClassType();
}
