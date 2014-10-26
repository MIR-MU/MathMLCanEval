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
package cz.muni.fi.mir.db.dao;

import java.util.List;

import cz.muni.fi.mir.db.domain.Annotation;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface AnnotationDAO extends GenericDAO<Annotation, Long>
{
    /**
     * Method obtains all Annotations from database in <b>DESCENDING</b> order.
     * So newer annotation are in the front, and the old ones in the back of
     * list.
     *
     * @return List with Annotations in descending order. If there are no
     * Annotations yet then empty List returned.
     */
    List<Annotation> getAllAnnotations();
}
