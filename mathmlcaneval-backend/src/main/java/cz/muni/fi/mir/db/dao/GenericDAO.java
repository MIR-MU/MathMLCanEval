/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import java.io.Serializable;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T> Type of entity managed by this class
 * @param <PK> primary key of entity
 */
public interface GenericDAO<T,PK extends Serializable>
{
    void create(T t);
    void update(T t);
    T getByID(PK id);
    void delete(PK id);
}
