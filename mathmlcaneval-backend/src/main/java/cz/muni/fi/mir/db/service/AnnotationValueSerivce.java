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
package cz.muni.fi.mir.db.service;

import java.util.List;

import cz.muni.fi.mir.db.domain.AnnotationValue;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AnnotationValueSerivce
{
    /**
     * Method creates given annotation value inside system
     * @param annotationValue to be created
     * @throws IllegalArgumentException if annotation value is null
     */
    void createAnnotationValue(AnnotationValue annotationValue) throws IllegalArgumentException;

    /**
     * Method updates given annotation value inside database.
     * @param annotationValue 
     * @throws IllegalArgumentException if annotation value is null, or does not have proper id
     */
    void updateAnnotationValue(AnnotationValue annotationValue) throws IllegalArgumentException;

    /**
     * Methods deletes given annotation value from database.
     * @param annotationValue to be deleted
     * @throws IllegalArgumentException if annotation value is null, or does not have proper id
     */
    void deleteAnnotationValue(AnnotationValue annotationValue) throws IllegalArgumentException;

    /**
     * Method obtains annotation value based on its id 
     * @param id of annotation
     * @return annotation with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or les than one
     */
    AnnotationValue getAnnotationValueByID(Long id) throws IllegalArgumentException;
    
    /**
     * method obtains annotation value based on its value. Value contains hashtag so partial match does not work.
     * @param value of annotation
     * @return annotation with given value
     * @throws IllegalArgumentException if value is null or has zero characters
     */
    AnnotationValue getAnnotationValueByValue(String value) throws IllegalArgumentException;

    /**
     * Method selects all annotations in systems and looks for annotation.
     * Annotation always starts with <b>&#35;</b> and when it is matched its
     * stored inside db. This method does not delete previous annotation just
     * add new ones.
     */
    void populate();

    /**
     * Method gets all annotation values from database ordered by their priority
     *
     * @return list of all annotation in ordered form, empty list if there are
     * none yet.
     */
    List<AnnotationValue> getAll();

    /**
     * Method obtains all annotation values from database that belongs to
     * formula class.
     *
     * @return list of all formula annotation values, empty list if there are
     * none yet
     */
    List<AnnotationValue> getAllForFormulas();

    /**
     * Method obtains all annotation values from database that belongs to
     * canonic output class.
     *
     * @return list of all canonic output annotation values, emptt list if there
     * are none yet
     */
    List<AnnotationValue> getAllForCanonicOutputs();
}
