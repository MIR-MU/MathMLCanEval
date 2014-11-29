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

import java.util.List;

import cz.muni.fi.mir.mathmlevaluation.db.domain.AnnotationValue;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AnnotationValueDAO extends GenericDAO<AnnotationValue, Long>
{

    /**
     * method obtains annotation value based on its value. Value contains
     * hashtag so partial match does not work.
     *
     * @param value of annotation
     * @return annotation with given value
     */
    AnnotationValue getByValue(String value);

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
