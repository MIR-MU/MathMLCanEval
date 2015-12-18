/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.mathmlcaneval.database;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T>
 * @param <PK>
 */
public interface GenericDAO<T, PK extends Serializable>
{
    void create(T entity);
    void update(T entity);
    T getByID(PK id);
    void delete(PK id);
    List<T> getAll();
}
