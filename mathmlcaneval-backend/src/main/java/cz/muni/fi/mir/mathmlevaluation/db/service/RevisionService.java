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

import cz.muni.fi.mir.mathmlevaluation.db.domain.Revision;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface RevisionService
{

    /**
     * Method creates given revision inside database.
     *
     * @param revision to be created
     * @throws IllegalArgumentException if revision is null
     */
    void createRevision(Revision revision) throws IllegalArgumentException;

    /**
     * Method deletes given revision from database.
     *
     * @param revision to be deleted
     * @throws IllegalArgumentException if revision is null or does not have
     * valid id.
     */
    void deleteRevision(Revision revision) throws IllegalArgumentException;

    /**
     * Method updates given revision inside database.
     *
     * @param revision to be updated
     * @throws IllegalArgumentException if revision is null or does not have set
     * its id.
     */
    void updateRevision(Revision revision) throws IllegalArgumentException;

    /**
     * Method obtains revision by its id from database.
     *
     * @param id of revision to be obtained
     * @return revision with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or less than one
     */
    Revision getRevisionByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains all revision from database.
     *
     * @return List of all revision, empty list if there are none yet.
     */
    List<Revision> getAllRevisions();
}
