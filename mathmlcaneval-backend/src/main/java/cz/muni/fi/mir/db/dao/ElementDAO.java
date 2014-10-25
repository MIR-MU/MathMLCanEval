/*
 * Copyright 2014 emptak.
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

import cz.muni.fi.mir.db.domain.Element;
import java.util.List;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ElementDAO extends GenericDAO<Element,Long>
{
    /**
     * Method obtains element out of database based on given name. In order to
     * match element by name, the match has to be exact.
     *
     * @param name of element to be found
     * @return Element with given name, null if there is no match
     */
    Element getElementByName(String name);
    
    /**
     * Method returns all elements out of database.
     *
     * @return list containing all elements, or empty list if there are none
     * yet.
     */
    List<Element> getAllElements();
}
