/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

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
