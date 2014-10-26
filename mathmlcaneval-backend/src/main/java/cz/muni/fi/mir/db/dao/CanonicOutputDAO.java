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
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;

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
