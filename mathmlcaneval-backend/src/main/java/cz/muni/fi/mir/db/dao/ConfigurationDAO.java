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

import cz.muni.fi.mir.db.domain.Configuration;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ConfigurationDAO extends GenericDAO<Configuration, Long>
{
    /**
     * Method fetches all Configurations out of database in <b>DESCENDING</b>
     * order.
     *
     * @return List of Configurations in descending order. If there are no
     * Configurations yet then empty List is returned.
     */
    List<Configuration> getAllCofigurations();
}
