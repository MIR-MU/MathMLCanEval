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

import java.util.Map;

import org.joda.time.DateTime;

import cz.muni.fi.mir.db.domain.Statistics;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface StatisticsService
{

    /**
     * Method calculates current statistics from database. Following entries are
     * being processed:
     * <ul>
     * <li>total number of formulas</li>
     * <li>total number of formulas having at least one caninicalization</li>
     * <li>total number of canonicalizations</li>
     * <li>total number of canonic outputs marked with valid annotation</li>
     * <li>total number of canonic outputs marked with invalid annotation</li>
     * <li>total number of canonic outputs marked with uncertain annotation</li>
     * <li>total number of canonic outputs marked for deletion</li>
     * <li>total number of formulas marked for deletion</li>
     * <li>total number of formulas that are meaningless</li>
     * </ul>
     */
    void calculate();

    /**
     * Method fetches given statistic based on id.
     *
     * @param id of statistic to be obtained
     * @return statistic with given id
     * @throws IllegalArgumentException if id is null or less than one.
     */
    Statistics getStatisticsByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains latest statistic stored inside database.
     *
     * @return latest statistic, null if there are non yet
     */
    Statistics getLatestStatistics();

    /**
     * Method generates map in form of &lt;ID,DateTime&gt; which serves as
     * dropdown menu. Thus user can choose statistics from date & time directly,
     * as date is bound to id.
     *
     * @return map in form of &lt;ID,DateTime&gt; of statistics
     */
    Map<Long, DateTime> getStatisticsMap();
}
