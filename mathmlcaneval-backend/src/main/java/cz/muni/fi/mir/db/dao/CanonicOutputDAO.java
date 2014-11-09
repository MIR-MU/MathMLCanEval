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

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Pagination;
import cz.muni.fi.mir.db.domain.SearchResponse;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CanonicOutputDAO extends GenericDAO<CanonicOutput, Long>
{

    /**
     * Method returns all canonic outputs that belong to given application run.
     * If pagination is set to null, then all results are returned. 
     *
     * @param applicationRun under which canonic outputs were made
     * @param pagination used for showing only subset of result
     * @return list of canonic outputs created under given application run.
     * Empty list if none were made.
     */
    SearchResponse<CanonicOutput> getCanonicOutputByAppRun(ApplicationRun applicationRun, Pagination pagination);

    /**
     * Method fetches canonic output based on given annotation from database.
     *
     * @param annotation to be fetched
     * @return canonic output matched against given annotation. Null if there is
     * no match.
     */
    CanonicOutput getCanonicOutputByAnnotation(Annotation annotation);
    
    /**
     * Method obtains canonic output based on its input hash
     * @param hashValue of canonic output
     * @return canonic output with given hash, null if there is no match
     */
    CanonicOutput getCanonicOuputByHashValue(String hashValue);
    
    /**
     * Method returns number of canonic outputs inside system.
     * @return number of canonic outputs
     */
    int getNumberOfCanonicOutputs();
    
    /**
     * Helper method of hashing
     * @param start starting point
     * @param end endin point
     * @return  list from start to end of canonic outputs
     */
    List<CanonicOutput> getSubListOfOutputs(int start, int end);
}
