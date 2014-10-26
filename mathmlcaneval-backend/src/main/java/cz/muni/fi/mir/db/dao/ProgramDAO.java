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

import cz.muni.fi.mir.db.domain.Program;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ProgramDAO extends GenericDAO<Program,Long>
{      
    /**
     * Method obtains programs based on their names and versions. There may be
     * multiple programs with same name and version but different input
     * parameters. Name and version is checked by exact match.
     *
     * @param name of program
     * @param version version of program
     * @return list of programs having given name and version
     */
    List<Program> getProgramByNameAndVersion(String name, String version);
    
    /**
     * Method loads all programs out of database.
     * @return list of all programs, empty list if there are none yet.
     */
    List<Program> getAllPrograms();    
}
