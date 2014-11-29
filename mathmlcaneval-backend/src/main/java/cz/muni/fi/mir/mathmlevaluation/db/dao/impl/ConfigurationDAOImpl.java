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
package cz.muni.fi.mir.mathmlevaluation.db.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import cz.muni.fi.mir.mathmlevaluation.db.dao.ConfigurationDAO;
import cz.muni.fi.mir.mathmlevaluation.db.domain.Configuration;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Repository(value = "configurationDAO")
public class ConfigurationDAOImpl extends GenericDAOImpl<Configuration, Long> implements ConfigurationDAO
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConfigurationDAOImpl.class);  
    
    public ConfigurationDAOImpl()
    {
        super(Configuration.class);
    }
    
    @Override
    public List<Configuration> getAllCofigurations()
    {
        List<Configuration> resultList = Collections.emptyList();
        
        try
        {
            resultList = entityManager.createQuery("SELECT c FROM configuration c", Configuration.class)
                    .getResultList();
        }
        catch(NoResultException nre)
        {
            logger.debug(nre);
        }
        
        return resultList;
    }
}
