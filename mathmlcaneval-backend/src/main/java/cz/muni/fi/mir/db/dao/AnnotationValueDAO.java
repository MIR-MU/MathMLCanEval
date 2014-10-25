/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.AnnotationValue;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AnnotationValueDAO extends GenericDAO<AnnotationValue, Long>
{
    AnnotationValue getByValue(String value);
    List<AnnotationValue> getAll();
}
