/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.domain.Annotation;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AnnotationService
{

    /**
     * Method obtains given annotation based on input id.
     *
     * @param id of annotation to be obtained
     * @return annotation with given id, null if there is no such annotation
     * with input id.
     * @throws IllegalArgumentException if id is null or less than one.
     */
    Annotation getAnnotationByID(Long id) throws IllegalArgumentException;
}
