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
package cz.muni.fi.mir.mathmlevaluation.db.service;

import java.util.List;

import cz.muni.fi.mir.mathmlevaluation.db.domain.Element;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Formula;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ElementService
{

    /**
     * Method creates given element inside database
     *
     * @param element to be created
     * @throws IllegalArgumentException if element is null
     */
    void createElement(Element element) throws IllegalArgumentException;

    /**
     * Method obtains element out of database based on given input id.
     *
     * @param id of element to be obtained
     * @return element with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or out of valid range
     */
    Element getElementByID(Long id) throws IllegalArgumentException;

    /**
     * Method returns all elements out of database.
     *
     * @return list containing all elements, or empty list if there are none
     * yet.
     */
    List<Element> getAllElements();

    /**
     * Method loads file from classpath location
     * <b>"other/mathmlelements.txt</b> containing MathML elements which are
     * later stored inside database.
     */
    void reCreate();
    
    /**
     * Method extracts elements from MathML representation
     * @param formula to be extracted
     * @return List of elements inside formula.
     */
    List<Element> extractElements(Formula formula);
}
