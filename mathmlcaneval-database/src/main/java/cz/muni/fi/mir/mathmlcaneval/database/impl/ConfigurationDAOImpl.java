/*
 * Copyright 2016 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.database.impl;

import cz.muni.fi.mir.mathmlcaneval.database.ConfigurationDAO;
import cz.muni.fi.mir.mathmlcaneval.database.domain.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository
public class ConfigurationDAOImpl extends AbstractDAO<Configuration, Long> implements ConfigurationDAO
{

    @Override
    public List<Configuration> getAllEnabled()
    {
        return getEntityManager()
                .createQuery("SELECT c FROM configuration c WHERE c.active = true ORDER BY c.id DESC", getClassType())
                .getResultList();
    }

    @Override
    public Class<Configuration> getClassType()
    {
        return Configuration.class;
    }
}
