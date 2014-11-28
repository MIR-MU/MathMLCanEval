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

import cz.muni.fi.mir.db.interceptors.CanonicOutputInterceptor;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.SearchResponse;

/**
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Rober Siska - xsiska2 at mail.muni.cz
 */
public interface CanonicOutputService
{

    /**
     * Method creates given canonic output inside database.
     *
     * @param canonicOutput to be created
     * @throws IllegalArgumentException if canonic output is null
     */
    void createCanonicOutput(CanonicOutput canonicOutput) throws IllegalArgumentException;

    /**
     * Method deletes given canonic output from database.
     *
     * @param canonicOutput to be deleted.
     * @throws IllegalArgumentException if canonic output is null or does not
     * have set id (null or less than one).
     */
    void deleteCanonicOutput(CanonicOutput canonicOutput) throws IllegalArgumentException;

    /**
     * Method obtains given canonic output based on its id.
     *
     * @param id of canonic output
     * @return canonic output with given id, null if there is no match
     * @throws IllegalArgumentException if canonic output is null or does not
     * have set id (null or less than one).
     */
    CanonicOutput getCanonicOutputByID(Long id) throws IllegalArgumentException;

    /**
     * Method fetches canonic output based on input annotation. In other words
     * finds canonic output having given annotation.
     *
     * @param annotation of canonic output to be found.
     * @return canonic output with having given annotation, or null if there is
     * no match.
     * @throws IllegalArgumentException if annotation is null, or does not have
     * set id (null or less than one).
     */
    CanonicOutput getCanonicOutputByAnnotation(Annotation annotation) throws IllegalArgumentException;

    /**
     * Method returns all canonic outputs that belong to given application run
     *
     * @param applicationRun under which canonic outputs were made
     * @param pagination used for showing only subset of result
     * @return list of canonic outputs created under given application run.
     * Empty list if none were made.
     */
    SearchResponse<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun, Pagination pagination);

    /**
     * Method adds annotation to given canonic output. Execution of this method
     * is tracked via {@link CanonicOutputAuditor#aroundCreateAnnotation(cz.muni.fi.mir.db.domain.CanonicOutput, cz.muni.fi.mir.db.domain.Annotation)
     * }.
     *
     * @param canonicOutput in which annotation will be put
     * @param annotation to be put in canonic output
     * @throws IllegalArgumentException if any of input entity is null or does
     * not have set id (null or less than one).
     */
    void annotateCannonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException;

    /**
     * Method deletes annotation from canonic output. Execution of this method
     * is tracked via {@link CanonicOutputAuditor#aroundDeleteAnnotation(cz.muni.fi.mir.db.domain.CanonicOutput, cz.muni.fi.mir.db.domain.Annotation)
     * }
     *
     * @param canonicOutput from which annotation will be removed
     * @param annotation to be removed
     * @throws IllegalArgumentException if any of input entity is null or does
     * not have set id (null or less than one).
     */
    void deleteAnnotationFromCanonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException;

    /**
     * Method updates canonicOutput inside database.
     *
     * @param canonicOutput to be updated
     * @throws IllegalArgumentException if canonicOutput does not have set it's ID, or
     * canonicOutput is null.
     */
    void updateCanonicOutput(CanonicOutput canonicOutput) throws IllegalArgumentException;

    /**
     * Method recalculates hashes for all canonic outputs.
     */
    void recalculateHashes();

    /**
     * Method returns next canonic output subsequent to given one belonging to
     * same application run.
     *
     * @param current current pointer in movement
     * @return subsequent canonic output to given one in same application run,
     * null if there is none
     * @throws IllegalArgumentException if current is null, or does not have id.
     */
    CanonicOutput nextInRun(CanonicOutput current) throws IllegalArgumentException;

    /**
     * Method returns previous canonic output precedent to given one belonging
     * to same application run.
     *
     * @param current current pointer in movement
     * @return precedent canonic output to given one in same application run,
     * null if there is none
     * @throws IllegalArgumentException if current is null, or does not have id.
     */
    CanonicOutput previousInRun(CanonicOutput current) throws IllegalArgumentException;

    /**
     * Method returns first canonic output in given run based on id in the same
     * application run.
     *
     * @param current current pointer in application run
     * @return first canonic output in given application run based on pointer,
     * null if current is first one
     * @throws IllegalArgumentException if current is null or does not have
     * valid id.
     */
    CanonicOutput firstInRun(CanonicOutput current) throws IllegalArgumentException;

    /**
     * Method returns last canonic output in given run based on id in the same
     * application run.
     *
     * @param current current pointer in application run
     * @return last canonic output in given application run based on pointer,
     * null if current is last one
     * @throws IllegalArgumentException if current is null or does not have
     * valid id.
     */
    CanonicOutput lastInRun(CanonicOutput current) throws IllegalArgumentException;

    /**
     * Method returns last canonic output of given formula.
     *
     * @param formula
     * @return last canonic output chronologically
     * @throws IllegalArgumentException if formula is null or does not have valid id.
     */
    CanonicOutput lastOfFormula(Formula formula) throws IllegalArgumentException;
}
