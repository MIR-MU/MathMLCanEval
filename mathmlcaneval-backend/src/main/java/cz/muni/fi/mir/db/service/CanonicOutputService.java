/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.service;

import cz.muni.fi.mir.db.audit.CanonicOutputAuditor;
import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.CanonicOutput;

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
     * Method adds annotation to given canonic output. Execution of this method
     * is tracked via {@link CanonicOutputAuditor#arroundCreateAnnotation(cz.muni.fi.mir.db.domain.CanonicOutput, cz.muni.fi.mir.db.domain.Annotation)
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
     * is tracked via {@link CanonicOutputAuditor#arroundDeleteAnnotation(cz.muni.fi.mir.db.domain.CanonicOutput, cz.muni.fi.mir.db.domain.Annotation)
     * }
     *
     * @param canonicOutput from which annotation will be removed
     * @param annotation to be removed
     * @throws IllegalArgumentException if any of input entity is null or does
     * not have set id (null or less than one).
     */
    void deleteAnnotationFromCanonicOutput(CanonicOutput canonicOutput, Annotation annotation) throws IllegalArgumentException;
}
