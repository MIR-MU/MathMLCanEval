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

import cz.muni.fi.mir.mathmlevaluation.db.domain.Configuration;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ConfigurationService
{

    /**
     * Method creates configuration.
     *
     * @param configuration to be created
     * @throws IllegalArgumentException if input is null
     */
    void createConfiguration(Configuration configuration) throws IllegalArgumentException;    

    /**
     * Method deletes configuration.
     *
     * @param configuration to be deleted
     * @throws IllegalArgumentException if configuration is null or does not
     * have valid id.
     */
    void deleteConfiguration(Configuration configuration) throws IllegalArgumentException;

    /**
     * Method obtains configuration by its id.
     *
     * @param id of configuration to be obtained
     * @return configuration with given id, null if there is no match
     * @throws IllegalArgumentException if id is null or out of valid range.
     */
    Configuration getConfigurationByID(Long id) throws IllegalArgumentException;

    /**
     * Method obtains all configurations out of database.
     *
     * @return all configurations in form of list. If there are no
     * configurations yet, then empty list is returned.
     */
    List<Configuration> getAllCofigurations();
}
