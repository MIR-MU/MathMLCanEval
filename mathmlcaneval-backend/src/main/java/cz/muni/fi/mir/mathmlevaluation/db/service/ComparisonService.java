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

import cz.muni.fi.mir.mathmlevaluation.db.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlevaluation.db.domain.CanonicOutput;
import java.util.Map;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ComparisonService
{

    /**
     * Method compares Canonic outputs in two given application runs. If canonic
     * output from application run 1 is different from one in application run 2,
     * then it is added to result map. Only common canonic outputs are compared.
     * E.g. if we have arun1{A,B,C} and arun2{B,C,D} only canonic outputs {B,C}
     * will be compared. If there are no changes (both runs have the same
     * result) then empty map is returned.
     *
     * @param applicationRun1 to be compared
     * @param applicationRun2 to be compared against
     * @return map of changed canonic outputs
     * @throws IllegalArgumentException if any of application run is null or does not have valid id.
     */
    Map<CanonicOutput, CanonicOutput> compare(ApplicationRun applicationRun1, ApplicationRun applicationRun2) throws IllegalArgumentException;
}
