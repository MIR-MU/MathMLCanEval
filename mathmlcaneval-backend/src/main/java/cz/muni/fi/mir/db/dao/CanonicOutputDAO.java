/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CanonicOutputDAO extends GenericDAO<CanonicOutput, Long>
{

    /**
     * Method returns all canonic outputs that belong ti given application run
     *
     * @param applicationRun under which canonic outputs were made
     * @return list of canonic outputs created under given application run.
     * Empty list if none were made.
     */
    List<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun);

    /**
     * Method fetches canonic output based on given annotation from database.
     *
     * @param annotation to be fetched
     * @return canonic output matched against given annotation. Null if there is
     * no match.
     */
    CanonicOutput getCanonicOutputByAnnotation(Annotation annotation);
}
